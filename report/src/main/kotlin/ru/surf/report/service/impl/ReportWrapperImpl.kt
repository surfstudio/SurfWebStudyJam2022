package ru.surf.report.service.impl

import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import ru.surf.report.model.CandidateExcelDto
import ru.surf.report.model.CandidatesReport
import ru.surf.report.model.EventReport
import ru.surf.report.service.ReportWrapper
import java.io.ByteArrayOutputStream


@Service
class ReportWrapperImpl(
    private val templateEngine: SpringTemplateEngine,
    private val converterProperties: ConverterProperties
) : ReportWrapper {

    override fun wrapToEventReport(report: EventReport): ByteArray {
        val context = Context()

        context.setVariable("report", report)
        val html = templateEngine.process("report", context)

        val target = ByteArrayOutputStream()
        HtmlConverter.convertToPdf(html, target, converterProperties)

        return target.toByteArray()
    }

    override fun wrapToCandidatesReport(candidatesReport: CandidatesReport): ByteArray {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Кандидаты")
        val header = sheet.createRow(0)

        header.createCell(0).setCellValue("First Name")
        header.createCell(1).setCellValue("Last Name")
        header.createCell(2).setCellValue("Email")
        header.createCell(3).setCellValue("Tags")

        candidatesReport.candidates.forEachIndexed { index: Int, candidate: CandidateExcelDto ->
            val row = sheet.createRow(index + 1)

            row.createCell(0).setCellValue(candidate.firstName)
            row.createCell(1).setCellValue(candidate.lastName)
            row.createCell(2).setCellValue(candidate.email)
            row.createCell(3).setCellValue(candidate.tags)
        }

        val target = ByteArrayOutputStream()
        workbook.write(target)

        return target.toByteArray()
    }
}