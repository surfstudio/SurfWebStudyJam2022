package ru.surf.report.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import ru.surf.report.service.ReportService
import ru.surf.report.service.ReportWrapper
import java.util.*


@Controller
@RequestMapping(value = ["/final_report"])
class ReportController(
    private val reportService: ReportService,
    private val reportWrapper: ReportWrapper
) {

    @GetMapping("/pdf/{event_id}")
    fun getPdf(@PathVariable(name = "event_id") eventId: UUID): ResponseEntity<ByteArray> {
        val report = reportWrapper.wrap(
            reportService.getReport(eventId)
        )

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .body(report)
    }
}