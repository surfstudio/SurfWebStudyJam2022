package ru.surf.auth.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.surf.auth.dto.AccessTokenDto
import ru.surf.auth.dto.AccountCredentialsDto
import ru.surf.auth.dto.ResetPassphraseDto
import ru.surf.auth.dto.ResponseAccountIdentityDto
import ru.surf.auth.entity.AccountIdentity
import ru.surf.auth.entity.ActivationIdentity
import ru.surf.auth.entity.Credentials
import ru.surf.auth.repository.CredentialsRepository
import ru.surf.auth.repository.IdentityRepository
import ru.surf.auth.service.AuthService
import ru.surf.auth.service.CredentialsService
import java.util.*


@Service
class CredentialsServiceImpl(
        @Autowired private val authService: AuthService,
        @Autowired private val credentialsRepository: CredentialsRepository,
        @Autowired private val identityRepository: IdentityRepository
) : CredentialsService {
    override fun createTemporaryIdentity(subjectId: UUID): UUID {
        return credentialsRepository.run {
            identityRepository.run {
                save(Credentials(identity = save(ActivationIdentity(subjectId))))
            }
        }.identity.let {
            (it as ActivationIdentity).activationId
        }
    }

    override fun authenticateSubject(activationId: UUID): UUID {
        return getCredentialsByActivationId(activationId).identity.let {
            (it as ActivationIdentity).subjectId
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Exception::class])
    override fun activateSubject(subjectId: UUID, accountCredentialsDto: AccountCredentialsDto) {
        getCredentialsBySubjectId(subjectId).apply {
            identity = identityRepository.run {
                save(AccountIdentity(accountCredentialsDto.identity))
            }
            credentialsRepository.save(this)
            credentialsRepository.flush()
            authService.register(AccountCredentialsDto(credentialsId, accountCredentialsDto.passphrase))
        }
    }

    override fun authenticateAccount(accountCredentialsDto: AccountCredentialsDto): AccessTokenDto {
        return credentialsRepository.run {
            findByIdentity(identityRepository.run {
                findByAccountId(accountCredentialsDto.identity)
            }).run {
                authService.login(AccountCredentialsDto(credentialsId, accountCredentialsDto.passphrase))
            }
        }
    }

    override fun authorizeAccount(accessTokenDto: AccessTokenDto): ResponseAccountIdentityDto {
        return credentialsRepository.run {
            findById(authService.validateToken(accessTokenDto).identity).get()
        }.identity.let {
            (it as AccountIdentity).accountId
        }.let { ResponseAccountIdentityDto(it) }
    }

    override fun resetAccountPassphrase(resetPassphraseDto: ResetPassphraseDto) {
        authService.resetPassword(resetPassphraseDto)
    }

    private fun getCredentialsByActivationId(activationId: UUID) : Credentials {
        return credentialsRepository.run {
            identityRepository.run {
                findByIdentity(findByActivationId(activationId))
            }
        }
    }

    private fun getCredentialsBySubjectId(subjectId: UUID) : Credentials {
        return credentialsRepository.run {
            identityRepository.run {
                findByIdentity(findBySubjectId(subjectId))
            }
        }
    }
}
