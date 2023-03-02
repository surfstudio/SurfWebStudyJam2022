package ru.surf.testing.dto.responce

import java.util.UUID

data class ScoreInfoResponseDto(
        val candidateId: UUID,
        val score: Double?
)
