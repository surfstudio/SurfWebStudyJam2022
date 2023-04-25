package ru.surf.report.service

import ru.surf.report.model.CandidatesReport
import ru.surf.report.model.EventReport

interface ReportWrapper {
    fun wrapToEventReport(report: EventReport): ByteArray
    fun wrapToCandidatesReport(candidatesReport: CandidatesReport): ByteArray
}