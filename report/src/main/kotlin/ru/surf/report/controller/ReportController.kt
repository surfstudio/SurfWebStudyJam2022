package ru.surf.report.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import ru.surf.report.service.CandidateReportService
import ru.surf.report.service.EventReportService
import ru.surf.report.service.ReportWrapper
import java.util.*


@Controller
@RequestMapping(value = ["/report"])
class ReportController(
    private val eventReportService: EventReportService,
    private val candidateReportService: CandidateReportService,
    private val reportWrapper: ReportWrapper
) {

    @GetMapping("event/{event_id}")
    fun getEventReport(@PathVariable(name = "event_id") eventId: UUID): ResponseEntity<ByteArray> {
        val report = reportWrapper.wrapToEventReport(
            eventReportService.getReport(eventId)
        )

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .body(report)
    }

    @GetMapping("event/{event_id}/candidates")
    fun getTraineeReport(@PathVariable(name = "event_id") eventId: UUID): ResponseEntity<ByteArray> {
        val report = reportWrapper.wrapToCandidatesReport(
            candidateReportService.getReport(eventId)
        )

        val header = HttpHeaders()
        header.contentType = MediaType("application", "force-download")
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.xlsx")

        return ResponseEntity.ok()
            .headers(header)
            .body(report)
    }
}