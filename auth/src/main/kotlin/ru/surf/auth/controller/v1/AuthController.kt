package ru.surf.auth.controller.v1

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheConfig
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.surf.auth.exception.RequestException
import ru.surf.auth.service.AuthService


@RestController
@RequestMapping("/auth/v1")
class AuthController(@Autowired private val authService: AuthService) {
    @PostMapping(value = ["/login"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun login(username: String?, password: String?): Any {
        username?.let {
            password?.let {
                @Suppress("unused") return object {
                    val accessToken = authService.login(username, password)
                }
            }
        }
        throw RequestException()
    }

    @PostMapping(value = ["/register"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun register(username: String?, password: String?): Any {
        username?.let {
            password?.let {
                authService.register(username, password)
                @Suppress("unused") return object {
                    val status = "success"
                }
            }
        }
        throw RequestException()
    }

    @PostMapping(value = ["/resetPassword"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun resetPassword(token: String?, password: String?): Any {
        token?.let {
            password?.let {
                authService.resetPassword(token, password)
                @Suppress("unused") return object {
                    val status = "success"
                }
            }
        }
        throw RequestException()
    }

    @PostMapping(value = ["/validate"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun validate(token: String?): Any {
        token?.let {
            @Suppress("unused") return object {
                val id = authService.validateToken(token)
            }
        }
        throw RequestException()
    }
}
