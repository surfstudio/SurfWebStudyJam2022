package ru.surf.report.controller

import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.thymeleaf.context.WebContext
import org.thymeleaf.spring6.SpringTemplateEngine
import org.thymeleaf.web.servlet.JakartaServletWebApplication
import ru.surf.report.service.ReportService
import java.io.ByteArrayOutputStream
import java.util.*


@Controller
@RequestMapping(value = ["/report"])
class ReportController(
    private val templateEngine: SpringTemplateEngine,
    private val request: HttpServletRequest,
    private val response: HttpServletResponse,
    private val reportService: ReportService,
    private val converterProperties: ConverterProperties
) {

    @GetMapping("/pdf/{event_id}")
    fun getPdf(@PathVariable(name = "event_id") eventId: UUID): ResponseEntity<ByteArray> {
        val context = webContext()

        context.setVariable("report", reportService.getPdfReport(eventId))
        val html = templateEngine.process("report", context)

        val target = ByteArrayOutputStream()
        HtmlConverter.convertToPdf(html, target, converterProperties)

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .body(target.toByteArray())
    }

    fun webContext(): WebContext {
        val application = JakartaServletWebApplication.buildApplication(request.servletContext)
        val exchange = application.buildExchange(request, response)
        return WebContext(exchange)
    }
}