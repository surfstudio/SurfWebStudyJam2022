package ru.surf.core.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.surf.auth.dto.AccountCredentialsDto
import ru.surf.auth.service.CredentialsService
import ru.surf.core.dto.candidate.CandidateApprovalDto
import ru.surf.core.dto.candidate.CandidateDto
import ru.surf.core.dto.candidate.CandidateEventNotificationDto
import ru.surf.core.dto.candidate.CandidatePromotionDto
import ru.surf.core.entity.*
import ru.surf.core.kafkaEvents.CandidateAppliedEvent
import ru.surf.core.mapper.candidate.CandidateMapper
import ru.surf.core.repository.CandidateRepository
import ru.surf.core.repository.EventRepository
import ru.surf.core.repository.EventTagRepository
import ru.surf.core.repository.TraineeRepository
import ru.surf.core.service.CandidateFilterService
import ru.surf.core.service.CandidateService
import ru.surf.core.service.EventService
import ru.surf.core.service.KafkaService
import ru.surf.externalfiles.dto.CandidateExcelDto
import ru.surf.externalfiles.service.S3FileService
import java.util.*

@Service
@DependsOn(value = ["credentialsServiceApiInvoker", "s3FileServiceApiHessianInvoker"])
class CandidateServiceImpl(
    @Autowired private val credentialsService: CredentialsService,
    @Autowired private val s3FileService: S3FileService,
    @Autowired private val candidateRepository: CandidateRepository,
    @Autowired private val traineeRepository: TraineeRepository,
    @Autowired private val candidateMapper: CandidateMapper,
    @Autowired private val kafkaService: KafkaService,
    @Autowired private val eventService: EventService,
    @Autowired private val eventTagRepository: EventTagRepository,
    @Autowired private val eventRepository: EventRepository,
    @Autowired private val candidateFilterService: CandidateFilterService
) : CandidateService {

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Exception::class])
    override fun createCandidate(candidateDto: CandidateDto): Candidate =
            candidateMapper.convertFromCandidateDtoToCandidateEntity(
                    candidateDto,
                    // todo временно, посмотреть mapstruct
                    event = eventService.getEvent(candidateDto.eventId).apply {
                        currentState?.stateType == EventState.StateType.APPLYING ||
                                throw Exception("Candidate application phase has already ended")
                    }
            ).also {
                candidateRepository.run {
                    save(it)
                    flush()
                }
                it.cvFileId = s3FileService.claimFile(candidateDto.cv.fileId)
                kafkaService.sendCoreEvent(
                        CandidateAppliedEvent(
                                emailTo = it.email,
                                candidate = it
                        )
                )
            }

    @Suppress("KotlinConstantConditions")
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Exception::class])
    override fun approveCandidate(candidate: Candidate): CandidateApprovalDto =
        candidateRepository.run {
            candidate.let {
                // TODO в этой ветке ещё нет кастомных исключений, добавить позже
                it.approved = !it.approved || throw Exception("candidate already approved!")
                save(it)
                flush()
            }
            CandidateApprovalDto(credentialsService.createCandidate(candidate.id))
        }


    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Exception::class])
    override fun promoteCandidate(candidate: Candidate, candidatePromotionDto: CandidatePromotionDto): Account =
        // TODO в черновом виде
        traineeRepository.run {
            save(Trainee(candidate = candidate)).apply {
                flush()
            }
        }.apply {
            credentialsService.promoteCandidate(
                candidate.id,
                AccountCredentialsDto(
                    identity = id,
                    passphrase = candidatePromotionDto.passphrase
                )
            )
        }

    override fun get(candidateId: UUID): Candidate = candidateRepository.findById(candidateId).orElseThrow {
        // TODO в этой ветке ещё нет кастомных исключений, добавить позже
        Exception("candidate not found")
    }

    // TODO: 25.02.2023 Скорее всего вынести в отдельный сервис
    override fun notifyCandidates(candidates: List<CandidateExcelDto>) {
        candidates.map { transform(it) }.forEach { kafkaService.sendCoreEvent(it) }
    }

    override fun getPreferredCandidates(eventId: UUID): Map<Candidate, List<String>> =
        candidateRepository.getCandidatesByEventId(eventId).run { candidateFilterService.filterCandidatesForm(this) }

    private fun transform(candidateExcelDto: CandidateExcelDto): CandidateEventNotificationDto {
        val eventTags =
            candidateExcelDto.tags.map { it.uppercase(Locale.getDefault()) }
                .mapNotNull { eventTagRepository.findEventTagByDescription(it) }.toSet()
        return CandidateEventNotificationDto(
            firstName = candidateExcelDto.firstName,
            lastName = candidateExcelDto.lastName,
            email = candidateExcelDto.email,
            eventsName = eventTags.map { eventRepository.findByEventTagsIn(setOf(it)) }
                .mapNotNull { it?.title }
                .distinct()
                .toSet()
        )
    }

}