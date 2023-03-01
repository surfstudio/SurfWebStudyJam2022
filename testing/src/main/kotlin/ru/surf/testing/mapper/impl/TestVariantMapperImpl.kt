package ru.surf.testing.mapper.impl

import org.springframework.stereotype.Component
import ru.surf.testing.dto.responce.TestInfoResponseDto
import ru.surf.testing.dto.responce.TestVariantResponseDto
import ru.surf.testing.entity.TestVariant
import ru.surf.testing.mapper.TestVariantMapper
import kotlin.random.Random

@Component
class TestVariantMapperImpl : TestVariantMapper {
    override fun toDto(testVariant: TestVariant): TestVariantResponseDto =
            TestVariantResponseDto(
                    testVariantId=testVariant.id,
                    candidateInfoDto= TestVariantResponseDto.CandidateInfoDto(
                            candidateId=testVariant.candidateInfo.id,
                            eventId=testVariant.candidateInfo.eventId
                    ),
                    testTemplateId=testVariant.testTemplate.id,
                    startedAt=testVariant.startedAt,
                    finishingAt=testVariant.finishingAt,
                    state=testVariant.state,
                    questionVariants=testVariant.questionVariants.map { questionVariant ->
                        TestVariantResponseDto.QuestionVariantDto(
                                questionId=questionVariant.id,
                                title=questionVariant.question.title,
                                questionWeight=questionVariant.question.weight,
                                totalPositiveAnswersWeight=questionVariant.question.totalPositiveAnswersWeight,
                                answerVariants=questionVariant.answerVariants.map { answerVariant ->
                                    TestVariantResponseDto.QuestionVariantDto.AnswerVariantDto(
                                            answerId=answerVariant.id,
                                            title=answerVariant.answer.title,
                                            weight=answerVariant.answer.weight
                                    )
                                }
                        )
                    }
            )

    override fun toTestInfoDto(testVariant: TestVariant): TestInfoResponseDto =
            TestInfoResponseDto(
                    testVariantId =testVariant.id,
                    maxAcceptableDurationSec =testVariant.testTemplate.maxAcceptableDurationSec,
                    finishingAt =testVariant.finishingAt,
                    currentQuestion =testVariant.currentQuestion?.let {
                        TestInfoResponseDto.QuestionInfoDto(
                                title=it.question.title,
                                questionType=it.question.questionType,
                                /*
                                    Перемешиваем варианты ответов в случайном порядке,
                                    но повторяемом в рамках одного вопроса
                                    (т.е. чтобы варианты ответов не "скакали" при перезагрузке страницы на фронте)
                                 */
                                answers=it.question.answers.shuffled(Random(it.id.hashCode())).map { answer ->
                                    TestInfoResponseDto.QuestionInfoDto.AnswerInfoDto(
                                            answerId=answer.id,
                                            title=answer.title
                                    )
                                }
                        )
                    },
                    state =testVariant.state
            )
}