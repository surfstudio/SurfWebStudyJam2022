package ru.surf.testing.dto.responce

import ru.surf.testing.entity.QuestionType
import java.util.*

data class TestTemplateResponseDto(
        val testTemplateId: UUID,
        val eventId: UUID,
        val maxAcceptableDurationSec: Long,
        val maxQuestionPoolSize: Int,
        val questions: List<QuestionDto>
) {
    data class QuestionDto(
            val questionId: UUID,
            val questionType: QuestionType,
            val title: String,
            val weight: Int,
            val answers: List<AnswerDto>
    ) {
        data class AnswerDto(
            val answerId: UUID,
            val title: String,
            val weight: Int,
        )
    }
}
