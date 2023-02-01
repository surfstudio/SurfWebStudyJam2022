package ru.surf.core.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import ru.surf.core.security.account.credentials.CredentialsAuthenticationFilter
import ru.surf.core.security.account.credentials.CredentialsAuthenticationManager
import ru.surf.core.security.account.token.TokenAuthenticationFilter
import ru.surf.core.security.account.token.TokenAuthenticationManager
import ru.surf.core.security.candidate.CandidateAuthenticationFilter
import ru.surf.core.security.candidate.CandidateAuthenticationManager
import javax.servlet.Filter


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
        @Autowired
        private val candidateAuthenticationManager: CandidateAuthenticationManager,
        @Autowired
        private val credentialsAuthenticationManager: CredentialsAuthenticationManager,
        @Autowired
        private val tokenAuthenticationManager: TokenAuthenticationManager
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.csrf { csrf ->
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
        get() = CandidateAuthenticationFilter().apply {
            setAuthenticationManager(candidateAuthenticationManager)
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
