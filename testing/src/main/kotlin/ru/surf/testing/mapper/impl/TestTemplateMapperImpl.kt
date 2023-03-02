package ru.surf.testing.mapper.impl

import org.springframework.stereotype.Component
import ru.surf.testing.dto.request.TestTemplatePutRequestDto
import ru.surf.testing.dto.responce.TestTemplateResponseDto
import ru.surf.testing.entity.*
import ru.surf.testing.mapper.TestTemplateMapper

@Component
class TestTemplateMapperImpl : TestTemplateMapper {
    override fun toEntity(testTemplatePutRequestDto: TestTemplatePutRequestDto): TestTemplate = TestTemplate(
                maxAcceptableDurationSec=testTemplatePutRequestDto.maxAcceptableDurationSec,
                maxQuestionPoolSize=testTemplatePutRequestDto.maxQuestionPoolSize,
                eventInfo=EventInfo(
                        id=testTemplatePutRequestDto.eventInfo.eventId,
                        expectedTestingPhaseDeadline=testTemplatePutRequestDto.eventInfo.expectedTestingPhaseDeadline,
                        testingPhaseState=TestingPhaseState.PENDING
                ),
                questions=testTemplatePutRequestDto.questions.map {
                    Question(
                            questionType=it.questionType,
                            title=it.title,
                            weight=it.weight,
                            answers=it.answers.map { answer ->
                                Answer(
                                        title=answer.title,
                                        weight=answer.weight
                                )
                            }.toSet()
                    )
                }.toSet()
        )

    override fun toDto(testTemplate: TestTemplate): TestTemplateResponseDto =
            TestTemplateResponseDto(
                    testTemplateId=testTemplate.id,
                    eventId=testTemplate.eventInfo.id,
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