package ru.surf.core.security.candidate

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import java.util.*
import javax.servlet.http.HttpServletRequest

class CandidateAuthenticationFilter : AbstractPreAuthenticatedProcessingFilter() {

    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest) {
    }

    override fun getPreAuthenticatedCredentials(request: HttpServletRequest): Any? {
        return request.getParameter("promotionId")?.let { UUID.fromString(it) }
    }

}