package ru.surf.core.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.surf.auth.dto.AccountCredentialsDto
import ru.surf.auth.service.CredentialsService
import ru.surf.core.dto.CandidateApprovalDto
import ru.surf.core.dto.CandidateDto
import ru.surf.core.dto.CandidatePromotionDto
import ru.surf.core.entity.Account
import ru.surf.core.entity.Candidate
import ru.surf.core.entity.StateEvent
import ru.surf.core.entity.Trainee
import ru.surf.core.event.ReceivingRequestKafkaEvent
import ru.surf.core.mapper.candidate.CandidateMapper
import ru.surf.core.repository.AccountRepository
import ru.surf.core.repository.CandidateRepository
import ru.surf.core.repository.EventRepository
import ru.surf.core.repository.TraineeRepository
import ru.surf.core.service.CandidateService
import ru.surf.core.service.EventService
import ru.surf.core.service.KafkaService
import ru.surf.externalfiles.service.S3FileService
import java.util.*

@Service
@DependsOn(value = ["credentialsServiceApiInvoker", "s3FileServiceApiHessianInvoker"])
class CandidateServiceImpl(
    @Autowired private val credentialsService: CredentialsService,
    @Autowired private val s3FileService: S3FileService,
    @Autowired private val candidateRepository: CandidateRepository,
    @Autowired private val eventRepository: EventRepository,
    @Autowired private val accountRepository: AccountRepository,
    @Autowired private val traineeRepository: TraineeRepository,
    @Autowired private val candidateMapper: CandidateMapper,
    @Autowired private val kafkaService: KafkaService,
    @Autowired private val eventService: EventService
) : CandidateService {

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Exception::class])
    override fun createCandidate(candidateDto: CandidateDto): Candidate =
        candidateMapper.convertFromCandidateDtoToCandidateEntity(candidateDto).apply {
            event = eventRepository.findById(candidateDto.eventId).orElseThrow {
                    // TODO в этой ветке ещё нет кастомных исключений, добавить позже
                    Exception("event not found")
                }.apply {
                    // TODO в черновом виде
                    statesEvents.any { it.stateType == StateEvent.StateType.CLOSED } && throw Exception("Event closed")
                }
        }.also {
            candidateRepository.run {
                save(it)
                flush()
            }
            it.cvFileId = s3FileService.claimFile(candidateDto.cv.fileId)
            kafkaService.sendReceivingRequestEvent(
                ReceivingRequestKafkaEvent(
                    candidateDto.email,
                    eventService.getEvent(candidateDto.eventId).title,
                    firstName = candidateDto.firstName,
                    lastName = candidateDto.lastName
                )
            )
        }

    @Suppress("KotlinConstantConditions")
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Exception::class])
    override fun approveCandidate(candidate: Candidate): CandidateApprovalDto =
        candidateRepository.run {
            candidate.let {
                // TODO в этой ветке ещё нет кастомных исключений, добавить позже
                it.isApproved = !it.isApproved || throw Exception("candidate already approved!")
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
                credentialsService.promoteCandidate(candidate.id,
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

}