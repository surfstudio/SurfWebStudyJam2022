package ru.surf.auth.controller.v1

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.surf.auth.dto.AccountCredentialsDto
import ru.surf.auth.dto.ResetPassphraseDto
import ru.surf.auth.dto.AccessTokenDto
import ru.surf.auth.dto.ResponseAccountIdentityDto
import ru.surf.auth.service.CredentialsService
import java.util.*


@RestController
@RequestMapping("/credentials/v1")
class CredentialsController(
        @Autowired private val credentialsService: CredentialsService,
) {
    @PostMapping(value = ["/createSubject/{subjectId}"])
    fun createSubject(@PathVariable subjectId: UUID): Any {
        @Suppress("unused") return object {
            val activationId = credentialsService.createTemporaryIdentity(subjectId)
            val subjectId = subjectId
        }
    }

    @PostMapping(value = ["/authenticateSubject/{activationId}"])
    fun authenticateSubject(@PathVariable activationId: UUID): Any {
        @Suppress("unused") return object {
            val subjectId = credentialsService.authenticateSubject(activationId)
        }
    }

    @PostMapping(value = ["/activateSubject/{subjectId}"])
    fun activateSubject(@PathVariable subjectId: UUID, @RequestBody dto: AccountCredentialsDto): Any {
        credentialsService.activateSubject(subjectId, dto)
        @Suppress("unused") return object {
            val identity = dto.identity
        }
    }

    @PostMapping(value = ["/authenticateAccount"])
    fun authenticateAccount(@RequestBody dto: AccountCredentialsDto): AccessTokenDto {
        return credentialsService.authenticateAccount(dto)
    }

    @PostMapping(value = ["/authorizeAccount"])
    fun authorizeAccount(@RequestBody dto: AccessTokenDto): ResponseAccountIdentityDto {
        return credentialsService.authorizeAccount(dto)
    }

    @PostMapping(value = ["/resetAccountPassphrase"])
    fun resetAccountPassphrase(@RequestBody dto: ResetPassphraseDto): Any {
        credentialsService.resetAccountPassphrase(dto)
        @Suppress("unused") return object {
            val status = "success"
        }
    }
}
