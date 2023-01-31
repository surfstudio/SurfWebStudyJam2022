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
import ru.surf.auth.entity.CandidateIdentity
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
    override fun createCandidate(candidateId: UUID): UUID {
        return credentialsRepository.run {
            identityRepository.run {
                save(Credentials(identity = save(CandidateIdentity(candidateId))))
            }
        }.identity.let {
            (it as CandidateIdentity).promotionId
        }
    }

    override fun authenticateCandidate(promotionId: UUID): UUID {
        return getCredentialsByPromotionId(promotionId).identity.let {
            (it as CandidateIdentity).candidateId
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Exception::class], timeout = 2)
    override fun promoteCandidate(candidateId: UUID, accountCredentialsDto: AccountCredentialsDto) {
        getCredentialsByCandidateId(candidateId).apply {
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

    private fun getCredentialsByPromotionId(promotionId: UUID) : Credentials {
        return credentialsRepository.run {
            identityRepository.run {
                findByIdentity(findByPromotionId(promotionId))
            }
        }
    }

    private fun getCredentialsByCandidateId(candidateId: UUID) : Credentials {
        return credentialsRepository.run {
            identityRepository.run {
                findByIdentity(findByCandidateId(candidateId))
            }
        }
    }
}
