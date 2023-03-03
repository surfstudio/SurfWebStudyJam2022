package ru.surf.testing.sharedDto

import java.util.UUID

data class CandidateScoreResponseDto(
        val candidateId: UUID,
        val score: Double?
)
