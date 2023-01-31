package ru.surf.auth.service

import ru.surf.auth.dto.AccountCredentialsDto
import ru.surf.auth.dto.ResetPassphraseDto
import ru.surf.auth.dto.AccessTokenDto
import ru.surf.auth.dto.ResponseAccountIdentityDto


interface AuthService {
    fun login(accountCredentialsDto: AccountCredentialsDto): AccessTokenDto
    fun register(accountCredentialsDto: AccountCredentialsDto)
    fun resetPassword(resetPassphraseDto: ResetPassphraseDto)
    fun validateToken(accessTokenDto: AccessTokenDto): ResponseAccountIdentityDto
}
