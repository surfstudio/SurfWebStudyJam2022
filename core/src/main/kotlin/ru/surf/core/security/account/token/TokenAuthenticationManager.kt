package ru.surf.core.security.account.token

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.stereotype.Component
import ru.surf.auth.dto.AccessTokenDto
import ru.surf.auth.service.CredentialsService
import ru.surf.core.service.AccountService

@Component
@DependsOn(value = ["credentialsServiceApiInvoker"])
@Primary
class TokenAuthenticationManager(
        @Autowired
        private val credentialsService: CredentialsService,

        @Autowired
        private val accountService: AccountService
) : AuthenticationManager {

    override fun authenticate(authentication: Authentication) : Authentication? {
        return authentication.credentials?.let { credentials ->
            try {
                credentialsService.authorizeAccount(AccessTokenDto(accessToken = credentials as String)).let {
                    PreAuthenticatedAuthenticationToken(
                            accountService.getAccountById(it.identity),
                            credentials
                    ).apply {
                        isAuthenticated = true
                    }
                }
            } catch (e: Exception) {
                null
            }
        }
    }

}