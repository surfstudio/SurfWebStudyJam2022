package ru.surf.core.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.surf.auth.dto.AccountCredentialsDto
import ru.surf.auth.service.CredentialsService
import ru.surf.core.dto.CredentialsDto
import ru.surf.core.entity.SurfEmployee
import ru.surf.core.repository.SurfEmployeeRepository
import ru.surf.core.service.SurfEmployeeService
import java.time.ZonedDateTime
import java.util.*

@Service
@DependsOn(value = ["credentialsServiceApiInvoker"])
class SurfEmployeeServiceImpl(

        @Autowired
        private val credentialsService: CredentialsService,

        @Autowired
        private val surfEmployeeRepository: SurfEmployeeRepository

) : SurfEmployeeService {

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Exception::class])
    override fun create(surfEmployee: SurfEmployee) = surfEmployeeRepository.run {
        save(surfEmployee).apply {
            flush()
        }
    }.run {
        Pair(this, credentialsService.createTemporaryIdentity(id))
    }

    override fun getSurfEmployee(id: UUID): SurfEmployee =
        surfEmployeeRepository.findByIdOrNull(id) ?: throw NoSuchElementException()

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Exception::class])
    override fun activateSurfEmployee(surfEmployee: SurfEmployee, credentialsDto: CredentialsDto): SurfEmployee =
            surfEmployeeRepository.run {
                surfEmployee.activatedAt = ZonedDateTime.now()
                save(surfEmployee).apply {
                    flush()
                }
            }.apply {
                credentialsService.activateSubject(
                        surfEmployee.id,
                        AccountCredentialsDto(
                                identity = surfEmployee.id,
                                passphrase = credentialsDto.passphrase
                        )
                )
            }

}