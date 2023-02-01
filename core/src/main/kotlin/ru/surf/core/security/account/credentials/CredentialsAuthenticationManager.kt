package ru.surf.core.security.account.credentials

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.stereotype.Component
import ru.surf.auth.dto.AccountCredentialsDto
import ru.surf.auth.service.CredentialsService
import ru.surf.core.service.AccountService

@Component
@DependsOn(value = ["credentialsServiceApiInvoker"])
class CredentialsAuthenticationManager(
        @Autowired
        private val credentialsService: CredentialsService,

        @Autowired
        private val accountService: AccountService
) : AuthenticationManager {

    override fun authenticate(authentication: Authentication) : Authentication? {
        return authentication.principal?.let { principal ->
            authentication.credentials?.let { credentials ->
                accountService.getAccountByEmail(principal as String)?.let { account ->
                    try {
                        PreAuthenticatedAuthenticationToken(
                                account,
                                credentialsService.authenticateAccount(AccountCredentialsDto(
                                        identity = account.id,
                                        passphrase = credentials as String
                                )).accessToken
                        ).apply {
                            isAuthenticated = true
                        }
                    } catch (e: Exception) {
                        null
                    }
                }
            }
        }
    }

}