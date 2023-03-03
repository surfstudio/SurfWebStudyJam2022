package ru.surf.testing.dto.request

import ru.surf.testing.entity.QuestionType
import java.time.ZonedDateTime
import java.util.*

data class TestTemplatePutRequestDto(
        val maxAcceptableDurationSec: Long,
        val maxQuestionPoolSize: Int,
        val eventInfo: EventInfoDto,
        val questions: Set<QuestionDto>
) {
    data class EventInfoDto(
            val eventId: UUID,
            val expectedTestingPhaseDeadline: ZonedDateTime
    )
    data class QuestionDto(
            val questionType: QuestionType,
            val title: String,
            val weight: Int,
            val answers: List<AnswerDto>
    ) {
        data class AnswerDto(
                val title: String,
                val weight: Int,
        )
    }
}
