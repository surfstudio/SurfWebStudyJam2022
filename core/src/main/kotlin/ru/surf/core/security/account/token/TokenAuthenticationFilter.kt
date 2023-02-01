package ru.surf.core.security.account.token

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import javax.servlet.http.HttpServletRequest

class TokenAuthenticationFilter : AbstractPreAuthenticatedProcessingFilter() {

    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest) {
    }

    override fun getPreAuthenticatedCredentials(request: HttpServletRequest): String? {
        return request.cookies?.find { it.name=="TOKEN" }?.value
    }

}