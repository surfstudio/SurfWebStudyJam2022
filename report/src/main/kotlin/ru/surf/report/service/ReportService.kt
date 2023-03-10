package ru.surf.report.service

import ru.surf.report.model.Report
import java.util.UUID

interface ReportService {
    fun getPdfReport(eventId: UUID) : Report
}