package ru.surf.auth.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import ru.surf.auth.converter.ObjectConverter
import ru.surf.auth.dto.AccountCredentialsDto
import ru.surf.auth.dto.ResetPassphraseDto
import ru.surf.auth.dto.AccessTokenDto
import ru.surf.auth.dto.ResponseAccountIdentityDto
import ru.surf.auth.exception.ConflictException
import ru.surf.auth.exception.UnauthorizedException
import ru.surf.auth.service.AuthService
import ru.surf.auth.service.JwtService
import java.util.*
import kotlin.collections.HashMap


@Service
class AuthServiceImpl(
        @Autowired
        private val restTemplate: RestTemplate,

        @Autowired
        private val jwtService: JwtService,

        @Autowired
        private val objectConverter: ObjectConverter,

        @Autowired
        @Lazy
        private val clientToken: ClientToken,

        @Value("\${keycloak.client-id}")
        private val clientId: String,

        @Value("\${keycloak.client-grant-type}")
        private val clientGrantType: String,

        @Value("\${keycloak.client-secret}")
        private val clientSecret: String,

        @Value("\${keycloak.user-grant-type}")
        private val userGrantType: String,

        @Value("\${keycloak.token-url}")
        private val keycloakTokenUrl: String,

        @Value("\${keycloak.user-info-url}")
        private val keycloakUserInfoUrl: String,

        @Value("\${keycloak.users-url}")
        private val keycloakUsersUrl: String,

        @Value("\${keycloak.reset-password-url}")
        private val keycloakResetPasswordUrl: String,
) : AuthService {
    override fun login(accountCredentialsDto: AccountCredentialsDto): AccessTokenDto {
        @Suppress("unused") val request = HttpEntity(object {
            val username = accountCredentialsDto.identity
            val password = accountCredentialsDto.passphrase
            val grant_type = userGrantType
            val client_id = clientId
            val client_secret = clientSecret
        }.let { objectConverter.convert(it) }, null)

        try {
            val response = restTemplate.postForObject(keycloakTokenUrl, request, Map::class.java)
            return AccessTokenDto(accessToken = response!!["access_token"] as String)
        } catch (ex: HttpClientErrorException.Unauthorized) {
            throw UnauthorizedException()
        }
    }

    override fun register(accountCredentialsDto: AccountCredentialsDto) {
        @Suppress("unused") val request = HttpEntity(object {
            val username = accountCredentialsDto.identity
            val enabled = true
            val credentials = listOf(object {
                val type = "password"
                val value = accountCredentialsDto.passphrase
                val temporary = false
            })
        }, object : LinkedMultiValueMap<String, String>() {
            init {
                add("Authorization", "Bearer ${clientToken.get}")
                add("Content-Type", "application/json")
            }
        })

        try {
            restTemplate.postForObject(keycloakUsersUrl, request, Void::class.java)
        } catch (ex: HttpClientErrorException.Conflict) {
            throw ConflictException()
        }
    }

    override fun resetPassword(resetPassphraseDto: ResetPassphraseDto) {
        val sub = jwtService.validateJwt(resetPassphraseDto.accessToken).run {
            getClaim("sub").asString()
        }

        @Suppress("unused") val request = HttpEntity(object {
            val type = "password"
            val value = resetPassphraseDto.newPassphrase
            val temporary = false
        }, object : LinkedMultiValueMap<String, String>() {
            init {
                add("Authorization", "Bearer ${clientToken.get}")
                add("Content-Type", "application/json")
            }
        })

        restTemplate.put(keycloakResetPasswordUrl, request, object : HashMap<String, String>() {
            init {
                put("sub", sub)
            }
        })
    }

    override fun validateToken(accessTokenDto: AccessTokenDto): ResponseAccountIdentityDto {
        return jwtService.validateJwt(accessTokenDto.accessToken).run {
            validateProfile(accessTokenDto.accessToken)
            getClaim("username").asString()
        }.let { ResponseAccountIdentityDto(identity = UUID.fromString(it)) }
    }

    private fun validateProfile(token: String) {
        val request = HttpEntity(null,
                object: LinkedMultiValueMap<String, String>() {
                    init {
                        add("Authorization", "Bearer $token")
                    }
                }
        )

        try {
            restTemplate.postForObject(keycloakUserInfoUrl, request, Any::class.java)
        } catch (ex: HttpClientErrorException.Unauthorized) {
            throw UnauthorizedException()
        }
    }

    @Component
    class ClientToken (@Autowired
                       private val authServiceImpl: AuthServiceImpl) {
        @get:Cacheable(cacheNames = ["ClientToken"], cacheManager="timeoutCacheManager")
        val get: String
            get() {
                with(authServiceImpl) {
                    @Suppress("unused") val request = HttpEntity(object {
                        val client_id = clientId
                        val grant_type = clientGrantType
                        val client_secret = clientSecret
                    }.let { objectConverter.convert(it) }, null)
                    val response = restTemplate.postForObject(keycloakTokenUrl, request, Map::class.java)
                    return response!!["access_token"] as String
                }
            }
    }
}
