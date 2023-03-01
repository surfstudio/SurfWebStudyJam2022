package ru.surf.core.security.account.credentials

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import jakarta.servlet.http.HttpServletRequest

class CredentialsAuthenticationFilter : AbstractPreAuthenticatedProcessingFilter() {

    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest): String? {
        return request.getHeader("login")
    }

    override fun getPreAuthenticatedCredentials(request: HttpServletRequest): Any? {
        return request.getHeader("password")
    }

}