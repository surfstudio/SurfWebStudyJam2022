package ru.surf.report.model

import java.util.UUID

data class TeamWithMentor(
    var id: UUID,
    var mentorName: String
)