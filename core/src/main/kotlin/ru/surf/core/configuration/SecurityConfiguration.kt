package ru.surf.core.configuration

import jakarta.servlet.Filter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import ru.surf.core.security.account.credentials.CredentialsAuthenticationFilter
import ru.surf.core.security.account.credentials.CredentialsAuthenticationManager
import ru.surf.core.security.account.token.TokenAuthenticationFilter
import ru.surf.core.security.account.token.TokenAuthenticationManager
import ru.surf.core.security.activation.ActivationAuthenticationFilter
import ru.surf.core.security.activation.ActivationAuthenticationManager


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfiguration(
        @Autowired
        private val activationAuthenticationManager: ActivationAuthenticationManager,
        @Autowired
        private val credentialsAuthenticationManager: CredentialsAuthenticationManager,
        @Autowired
        private val tokenAuthenticationManager: TokenAuthenticationManager,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.
        csrf { csrf ->
            csrf.disable()
        }.httpBasic { httpSecurity ->
            httpSecurity.disable()
        }.sessionManagement { sessionManagement ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }.
        addFilter(candidateFilter).
        addFilter(accountCredentialsFilter).
        addFilter(tokenAuthenticationFilter).
        headers { headers->
            headers.
            frameOptions().
            disable()
        }.build()
    }

    protected val candidateFilter: Filter
        get() = ActivationAuthenticationFilter().apply {
            setAuthenticationManager(activationAuthenticationManager)
        }

    protected val accountCredentialsFilter: Filter
        get() = CredentialsAuthenticationFilter().apply {
            setAuthenticationManager(credentialsAuthenticationManager)
        }

    protected val tokenAuthenticationFilter: Filter
        get() = TokenAuthenticationFilter().apply {
            setAuthenticationManager(tokenAuthenticationManager)
        }

}
