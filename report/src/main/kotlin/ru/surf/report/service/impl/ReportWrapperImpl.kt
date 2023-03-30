package ru.surf.report.service.impl

import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import ru.surf.report.model.Report
import ru.surf.report.service.ReportWrapper
import java.io.ByteArrayOutputStream


@Service
class ReportWrapperImpl(
    private val templateEngine: SpringTemplateEngine,
    private val converterProperties: ConverterProperties
) : ReportWrapper {

    override fun wrap(report: Report): ByteArray {
        val context = Context()

        context.setVariable("report", report)
        val html = templateEngine.process("report", context)

        val target = ByteArrayOutputStream()
        HtmlConverter.convertToPdf(html, target, converterProperties)

        return target.toByteArray()
    }
}