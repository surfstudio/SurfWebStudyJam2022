package ru.surf.testing.dto

import java.util.*

data class CandidateInfoFullDto(
        val candidateId: UUID,
        val firstName: String,
        val lastName: String,
        val email: String,
        val eventId: UUID
)