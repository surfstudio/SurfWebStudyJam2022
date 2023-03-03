package ru.surf.testing.sharedDto

import java.util.*

data class CandidateScoresResponseDto(
        val eventId: UUID,
        val scores: List<CandidateScoreResponseDto>
)
