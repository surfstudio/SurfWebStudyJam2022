package ru.surf.testing.dto.responce

import ru.surf.testing.entity.QuestionType
import ru.surf.testing.entity.TestVariant
import java.time.ZonedDateTime
import java.util.*

data class TestInfoResponseDto(
        val testVariantId: UUID,
        val maxAcceptableDurationSec: Long,
        val finishingAt: ZonedDateTime?,
        val currentQuestion: QuestionInfoDto?,
        val state: TestVariant.TestState
) {
    data class QuestionInfoDto(
            val title: String,
            val questionType: QuestionType,
            val answers: List<AnswerInfoDto>
    ) {
        data class AnswerInfoDto(
            val answerId: UUID,
            val title: String
        )
    }
}
