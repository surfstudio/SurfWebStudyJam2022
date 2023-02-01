package ru.surf.auth.controller.v1

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.surf.auth.dto.AccountCredentialsDto
import ru.surf.auth.dto.ResetPassphraseDto
import ru.surf.auth.dto.AccessTokenDto
import ru.surf.auth.dto.ResponseAccountIdentityDto
import ru.surf.auth.service.AuthService


@RestController
@RequestMapping("/auth/v1")
class AuthController(
        @Autowired private val authService: AuthService
) {
    @PostMapping(value = ["/login"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun login(@RequestBody dto: AccountCredentialsDto): AccessTokenDto {
        return authService.login(dto)
    }

    @PostMapping(value = ["/register"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun register(@RequestBody dto: AccountCredentialsDto): Any {
        authService.register(dto)
        return ResponseEntity.ok()
    }

    @PostMapping(value = ["/resetPassword"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun resetPassword(@RequestBody dto: ResetPassphraseDto): Any {
        authService.resetPassword(dto)
        return ResponseEntity.ok()
    }

    @PostMapping(value = ["/validate"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun validate(@RequestBody dto: AccessTokenDto): ResponseAccountIdentityDto {
        return authService.validateToken(dto)
    }
}
