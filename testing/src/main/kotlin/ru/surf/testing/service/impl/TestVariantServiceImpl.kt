package ru.surf.testing.service.impl

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.surf.testing.applicationEvents.TestTemplateReplaced
import ru.surf.testing.dto.request.AnswerRequestDto
import ru.surf.testing.entity.*
import ru.surf.testing.exception.*
import ru.surf.testing.repository.CandidateInfoRepository
import ru.surf.testing.repository.TestVariantRepository
import ru.surf.testing.service.TestVariantService
import java.time.ZonedDateTime
import java.util.*

@Service
class TestVariantServiceImpl(

        @Autowired
        private val testVariantRepository: TestVariantRepository,

        @Autowired
        private val candidateInfoRepository: CandidateInfoRepository

) : TestVariantService,
    ApplicationListener<TestTemplateReplaced> {

    companion object ClassLogger {
        val logger: Logger = LoggerFactory.getLogger(TestVariantServiceImpl::class.java)
    }

    override fun create(testTemplate: TestTemplate, candidateId: UUID): TestVariant =
        getByCandidateId(candidateId)?.run {
            if (state != TestVariant.TestState.NOT_STARTED) {
                throw TestStartedException(id, candidateId)
            }
            logger.info("TestVariant for candidateId = $candidateId already exists. Overwriting...")
            candidateInfo.also {
                testVariantRepository.delete(this)
            }
        }.run {
            logger.info("Creating TestVariant for $candidateId ...")
            TestVariant(
                    candidateInfo=this ?: CandidateInfo(
                            id=candidateId,
                            eventId=testTemplate.eventId
                    ),
                    startedAt=null,
                    testTemplate=testTemplate,
                    questionVariants=testTemplate.
                                questions.
                                shuffled().
                                take(testTemplate.maxQuestionPoolSize).
                                mapIndexed { index, it -> QuestionVariant(
                                     questionOrder=index,
                                     answeredAt=null,
                                     question=it,
                                     answerVariants=mutableSetOf()
                                )}.toSet()
            ).let {
                testVariantRepository.save(it)
            }
        }

    override fun startTest(testVariantId: UUID): TestVariant =
            get(testVariantId).run {
            if (state != TestVariant.TestState.NOT_STARTED) {
                throw TestStartedException(id, candidateInfo.id)
            }
            startedAt = ZonedDateTime.now()
            testVariantRepository.save(this)
        }


    override fun submitCurrentAnswerAndMoveNext(answerRequestDto: AnswerRequestDto): TestVariant =
            get(answerRequestDto.testVariantId).also {
                if (it.state != TestVariant.TestState.STARTED) {
                    throw TestNotStartedException(it.id, it.candidateInfo.id, it.state)
                }
                it.currentQuestion!!.apply {
                    if (answered) {
                        throw BadAnswerException(it.id, it.candidateInfo.id, "Attempt to answer already answered question")
                    }
                    answerVariants.addAll(
                            question.answers.filter { answer ->
                                answer.id in answerRequestDto.chosenAnswersId
                            }.map { answer ->
                                AnswerVariant(
                                        answer = answer
                                )
                            }
                    )
                    if (answerVariants.size != answerRequestDto.chosenAnswersId.size) {
                        throw BadAnswerException(it.id, it.candidateInfo.id, "Bad answerId(s) provided")
                    }
                    question.questionType.validate(answerVariants)?.let { reason ->
                        throw BadAnswerException(it.id, it.candidateInfo.id, reason)
                    }
                    answeredAt = ZonedDateTime.now()
                    testVariantRepository.save(it)
                }
            }


    override fun computeCandidateScore(candidateId: UUID) =
        when(val testVariant = getByCandidateId(candidateId)) {
            null -> throw NoSuchCandidateException(candidateId)
            else -> when (testVariant.state) {
                TestVariant.TestState.FINISHED -> testVariant.questionVariants.sumOf { questionVariant ->
                    questionVariant.totalAnswersWeight /
                            questionVariant.question.totalPositiveAnswersWeight *
                            questionVariant.question.weight
                }.div(testVariant.totalWeight)
                else -> null
            }
        }

    override fun get(testVariantId: UUID): TestVariant =
            testVariantRepository.findByIdOrNull(testVariantId) ?: throw NoSuchTestException(testVariantId)

    override fun getByCandidateId(candidateId: UUID): TestVariant? =
            testVariantRepository.findByCandidateInfoId(candidateId)

    override fun getAll(): List<TestVariant> =
            testVariantRepository.findAll()

    override fun getAllByEventId(eventID: UUID): List<CandidateInfo> =
            candidateInfoRepository.
            findAllByEventId(eventID)

    override fun onApplicationEvent(event: TestTemplateReplaced) {
        getAllByEventId(event.template.eventId).apply {
            logger.info(
                    "Found ${count()} " +
                    "test variants for test template with eventId = ${event.template.eventId}. " +
                    "Recreating...")
            forEach { create(event.template, it.id) }
        }
    }

}