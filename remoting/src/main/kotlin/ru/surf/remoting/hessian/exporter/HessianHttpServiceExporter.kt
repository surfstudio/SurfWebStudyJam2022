package ru.surf.remoting.hessian.exporter

import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.HttpRequestHandler
import org.springframework.web.HttpRequestMethodNotSupportedException
import java.io.IOException


class HessianHttpServiceExporter<T : Any>(
        serviceInterface: Class<in T>,
        service: T,
) : HessianExporter<T>(serviceInterface, service), HttpRequestHandler {

    @Throws(ServletException::class, IOException::class)
    override fun handleRequest(request: HttpServletRequest, response: HttpServletResponse) {
        if ("POST" != request.method) {
            throw HttpRequestMethodNotSupportedException(request.method, listOf("POST"))
        }
        response.contentType = CONTENT_TYPE_HESSIAN
        try {
            this(request.inputStream, response.outputStream)
        } catch (ex: Throwable) {
            throw ServletException("Hessian skeleton invocation failed", ex)
        }
    }

}