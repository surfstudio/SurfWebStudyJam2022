package ru.surf.core.dto

import java.util.*

data class CandidateDto(
        val name: String,
        val email: String,
        val eventId: UUID
)
