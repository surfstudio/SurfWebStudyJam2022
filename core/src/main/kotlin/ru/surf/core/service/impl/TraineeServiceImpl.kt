package ru.surf.core.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.surf.auth.dto.AccountCredentialsDto
import ru.surf.auth.service.CredentialsService
import ru.surf.core.dto.candidate.CredentialsDto
import ru.surf.core.entity.Candidate
import ru.surf.core.entity.Trainee
import ru.surf.core.exception.trainee.TraineeNotFoundByIdException
import ru.surf.core.repository.TraineeRepository
import ru.surf.core.service.TraineeService
import java.util.*

@Service
@DependsOn(value = ["credentialsServiceApiInvoker"])
class TraineeServiceImpl(

    @Autowired private val credentialsService: CredentialsService,
    @Autowired private val traineeRepository: TraineeRepository,

) : TraineeService {

    override fun getTrainee(id: UUID): Trainee =
        traineeRepository.findByIdOrNull(id) ?: throw TraineeNotFoundByIdException(id)


    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Exception::class])
    override fun activateCandidate(candidate: Candidate, credentialsDto: CredentialsDto): Trainee =
        traineeRepository.run {
            save(Trainee(candidate = candidate)).apply {
                flush()
            }
        }.apply {
            credentialsService.activateSubject(
                candidate.id,
                AccountCredentialsDto(
                    identity = id,
                    passphrase = credentialsDto.passphrase
                )
            )
        }

}