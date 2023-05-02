package ru.surf.report.service

import ru.surf.report.model.CandidatesReport
import java.util.UUID

interface CandidateReportService {
    fun getReport(eventId: UUID): CandidatesReport
    fun saveReport(reportByteArray: ByteArray, eventId: UUID)
}