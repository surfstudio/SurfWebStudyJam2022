package ru.surf.core.security.activation

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import java.util.*
import jakarta.servlet.http.HttpServletRequest

class ActivationAuthenticationFilter : AbstractPreAuthenticatedProcessingFilter() {

    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest) {
    }

    override fun getPreAuthenticatedCredentials(request: HttpServletRequest): Any? {
        return request.getParameter("activationId")?.let { UUID.fromString(it) }
    }

}