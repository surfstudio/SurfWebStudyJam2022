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
import ru.surf.core.entity.Trainee
import ru.surf.core.mapper.candidate.CandidateMapper
import ru.surf.core.repository.AccountRepository
import ru.surf.core.repository.CandidateRepository
import ru.surf.core.repository.EventRepository
import ru.surf.core.repository.TraineeRepository
import ru.surf.core.service.CandidateService
import java.util.*

@Service
@DependsOn(value = ["credentialsServiceApiInvoker"])
class CandidateServiceImpl(
        @Autowired private val credentialsService: CredentialsService,
        @Autowired private val candidateRepository: CandidateRepository,
        @Autowired private val eventRepository: EventRepository,
        @Autowired private val accountRepository: AccountRepository,
        @Autowired private val traineeRepository: TraineeRepository,
        @Autowired private val candidateMapper: CandidateMapper,
) : CandidateService {

    override fun createCandidate(candidateDto: CandidateDto): Candidate =
            candidateMapper.convertFromCandidateDtoToCandidateEntity(candidateDto).apply {
                events.add(
                        eventRepository.findById(candidateDto.eventId).orElseThrow {
                            // TODO в этой ветке ещё нет кастомных исключений, добавить позже
                            Exception("event not found")
                        }.apply {
                            // TODO в черновом виде
                            statesEvents.any{ it.stateType.type == "CLOSED" } && throw Exception("Event closed")
                        }
                )
            }.let {
                candidateRepository.save(it)
            }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Exception::class])
    override fun approveCandidate(candidate: Candidate): CandidateApprovalDto =
            candidateRepository.run {
                candidate.let {
                    // TODO в этой ветке ещё нет кастомных исключений, добавить позже
                    it.isApproved && throw Exception("candidate already approved!")
                    it.isApproved = true
                    save(it)
                    flush()
                }
                CandidateApprovalDto(credentialsService.createCandidate(candidate.id))
            }


    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Exception::class])
    override fun promoteCandidate(candidate: Candidate, candidatePromotionDto: CandidatePromotionDto): Account =
            // TODO в черновом виде
            accountRepository.run {
                save(Account(
                    email = candidate.email
                )).apply {
                    flush()
                }
            }.apply {
                traineeRepository.run {
                    save(Trainee(
                            isActive = true,
                            candidate = candidate,
                            account = this@apply,
                            event = candidate.events.last()
                    )).apply {
                        flush()
                    }
                }
            }.apply {
                credentialsService.promoteCandidate(candidate.id, AccountCredentialsDto(
                        identity = id,
                        passphrase = candidatePromotionDto.passphrase
                ))
            }

    override fun get(candidateId: UUID): Candidate = candidateRepository.findById(candidateId).orElseThrow {
        // TODO в этой ветке ещё нет кастомных исключений, добавить позже
        Exception("candidate not found")
    }

}