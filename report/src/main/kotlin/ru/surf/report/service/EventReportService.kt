package ru.surf.report.service

import ru.surf.report.model.EventReport
import java.util.UUID

interface EventReportService {
    fun getReport(eventId: UUID): EventReport
    fun saveReport(reportByteArray: ByteArray, eventId: UUID)
}