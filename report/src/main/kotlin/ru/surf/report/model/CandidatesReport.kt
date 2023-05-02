package ru.surf.report.model

import java.util.UUID

data class CandidatesReport(
    val eventId: UUID,
    var candidates: List<CandidateExcelDto>
)
