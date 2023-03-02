package ru.surf.testing.mapper.impl

import org.springframework.stereotype.Component
import ru.surf.testing.dto.responce.TestTemplateResponseDto
import ru.surf.testing.entity.TestTemplate
import ru.surf.testing.mapper.TestTemplateMapper

@Component
class TestTemplateMapperImpl : TestTemplateMapper {
    override fun toDto(testTemplate: TestTemplate): TestTemplateResponseDto =
            TestTemplateResponseDto(
                    testTemplateId=testTemplate.id,
                    eventId=testTemplate.eventId,
                    maxAcceptableDurationSec=testTemplate.maxAcceptableDurationSec,
                    maxQuestionPoolSize=testTemplate.maxQuestionPoolSize,
                    questions=testTemplate.questions.map {
                        TestTemplateResponseDto.QuestionDto(
                                questionId=it.id,
                                questionType=it.questionType,
                                title=it.title,
                                weight=it.weight,
                                answers=it.answers.map { answer ->
                                    TestTemplateResponseDto.QuestionDto.AnswerDto(
                                            answerId=answer.id,
                                            title=answer.title,
                                            weight=answer.weight
                                    )
                                }
                        )
                    }
            )
}