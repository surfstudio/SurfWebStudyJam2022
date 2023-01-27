package ru.surf.auth.service.impl

import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import ru.surf.auth.converter.ObjectConverter
import ru.surf.auth.exception.ConflictException
import ru.surf.auth.exception.UnauthorizedException
import ru.surf.auth.service.AuthService
import ru.surf.auth.service.JwtService


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
    override fun login(username: String, password: String): String {
        @Suppress("unused") val request = HttpEntity(object {
            val username = username
            val password = password
            val grant_type = userGrantType
            val client_id = clientId
            val client_secret = clientSecret
        }.let { objectConverter.convert(it) }, null)

        try {
            val response = restTemplate.postForObject(keycloakTokenUrl, request, Map::class.java)
            return response?.get("access_token") as String? ?: throw UnknownError()
        } catch (ex: HttpClientErrorException.Unauthorized) {
            throw UnauthorizedException()
        }
    }

    override fun register(username: String, password: String) {
        @Suppress("unused") val request = HttpEntity(object {
            val username = username
            val enabled = true
            val credentials = listOf(object {
                val type = "password"
                val value = password
                val temporary = false
            })
        }, object : LinkedMultiValueMap<String, String>() {
            init {
                add("Authorization", "Bearer ${clientToken.get}")
                add("Content-Type", "application/json")
            }
        })

        try {
            restTemplate.postForObject(keycloakUsersUrl, request, Any::class.java)
        } catch (ex: HttpClientErrorException.Conflict) {
            throw ConflictException()
        }
    }

    override fun resetPassword(token: String, password: String) {
        val jwt: DecodedJWT
        try {
            jwt = jwtService.validateJwt(token)
        } catch (ex: Exception) {
            throw UnauthorizedException()
        }
        val sub = jwt.getClaim("sub").asString()

        @Suppress("unused") val request = HttpEntity(object {
            val type = "password"
            val value = password
            val temporary = false
        }, object : LinkedMultiValueMap<String, String>() {
            init {
                add("Authorization", "Bearer ${clientToken.get}")
                add("Content-Type", "application/json")
            }
        })

        try {
            restTemplate.put(keycloakResetPasswordUrl, request, object : HashMap<String, String>() {
                init {
                    put("sub", sub)
                }
            })
        } catch (ex: Exception) {
            throw Exception("Bad response")
        }
    }

    override fun validateToken(token: String): String {
        val jwt: DecodedJWT
        try {
            jwt = jwtService.validateJwt(token)
        } catch (ex: Exception) {
            throw UnauthorizedException()
        }

        validateProfile(token)
        return jwt.getClaim("username").asString()
    }

    private fun validateProfile(token: String): Any {
        val request = HttpEntity<MultiValueMap<String, String>?>(null,
                object: LinkedMultiValueMap<String, String>() {
                    init {
                        add("Authorization", "Bearer $token")
                    }
                }
        )

        try {
            return restTemplate.postForObject(keycloakUserInfoUrl, request, Any::class.java)
                    ?: Exception("Bad response")
        } catch (ex: HttpClientErrorException.Unauthorized) {
            throw UnauthorizedException()
        }
    }

    @Component
    @Lazy
    class ClientToken @Autowired constructor(private val authServiceImpl: AuthServiceImpl) {
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
                    return response?.get("access_token") as String? ?: throw Exception("Bad response")
                }
            }
    }
}
