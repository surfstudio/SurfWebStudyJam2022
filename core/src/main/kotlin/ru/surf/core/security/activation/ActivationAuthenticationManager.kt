package ru.surf.core.security.activation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.stereotype.Component
import ru.surf.auth.service.CredentialsService
import ru.surf.core.service.AccountService
import java.util.*

@Component
@DependsOn(value = ["credentialsServiceApiInvoker"])
class ActivationAuthenticationManager(
        @Autowired
        private val credentialsService: CredentialsService,

        @Autowired
        private val accountService: AccountService
) : AuthenticationManager {

    override fun authenticate(authentication: Authentication) : Authentication? {
        return authentication.credentials?.let {
            try {
                PreAuthenticatedAuthenticationToken(
                        accountService.getAccountById(credentialsService.authenticateSubject(it as UUID)),
                        authentication.credentials
                ).apply {
                    isAuthenticated = true
                }
            } catch (e: Exception) {
                null
            }
        }
    }

}