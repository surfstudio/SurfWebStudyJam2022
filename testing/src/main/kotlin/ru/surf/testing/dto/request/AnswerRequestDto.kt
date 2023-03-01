package ru.surf.testing.dto.request

import java.util.*

data class AnswerRequestDto(
        val testVariantId: UUID,
        val chosenAnswersId: List<UUID>
)
