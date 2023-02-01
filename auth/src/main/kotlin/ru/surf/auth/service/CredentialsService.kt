package ru.surf.auth.service

import ru.surf.auth.dto.AccountCredentialsDto
import ru.surf.auth.dto.ResetPassphraseDto
import ru.surf.auth.dto.AccessTokenDto
import ru.surf.auth.dto.ResponseAccountIdentityDto
import java.util.UUID


interface CredentialsService {
    fun createCandidate(candidateId: UUID): UUID
    fun authenticateCandidate(promotionId: UUID): UUID
    fun promoteCandidate(candidateId: UUID, accountCredentialsDto: AccountCredentialsDto)
    fun authenticateAccount(accountCredentialsDto: AccountCredentialsDto): AccessTokenDto
    fun authorizeAccount(accessTokenDto: AccessTokenDto): ResponseAccountIdentityDto
    fun resetAccountPassphrase(resetPassphraseDto: ResetPassphraseDto)
}
