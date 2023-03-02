package ru.surf.testing.dto.responce

import ru.surf.testing.entity.TestVariant
import java.time.ZonedDateTime
import java.util.*

data class TestVariantResponseDto(
        val testVariantId: UUID,
        val candidateInfo: CandidateInfoDto,
        val testTemplateId: UUID,
        val startedAt: ZonedDateTime?,
        val finishingAt: ZonedDateTime?,
        val state: TestVariant.TestState,
        val questionVariants: List<QuestionVariantDto>,
) {
    data class CandidateInfoDto(
            val candidateId: UUID,
            val eventId: UUID
    )

    data class QuestionVariantDto(
            val questionId: UUID,
            val title: String,
            val questionWeight: Int,
            val totalPositiveAnswersWeight: Double,
            val answerVariants: List<AnswerVariantDto>
    ) {
        data class AnswerVariantDto(
                val answerId: UUID,
                val title: String,
                val weight: Int
        )
    }
}
