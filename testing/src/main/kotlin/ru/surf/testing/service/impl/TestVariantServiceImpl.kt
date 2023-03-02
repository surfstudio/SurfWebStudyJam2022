package ru.surf.testing.service.impl

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Service
import ru.surf.core.kafkaEvents.TestCreatedEvent
import ru.surf.core.kafkaEvents.TestPassedEvent
import ru.surf.testing.applicationEvents.TestTemplateReplaced
import ru.surf.testing.applicationEvents.TestingPhaseStateChanged
import ru.surf.testing.dto.request.AnswerRequestDto
import ru.surf.testing.entity.*
import ru.surf.testing.exception.*
import ru.surf.testing.repository.TestVariantRepository
import ru.surf.testing.service.CandidateInfoService
import ru.surf.testing.service.KafkaService
import ru.surf.testing.service.TestVariantService
import java.time.ZonedDateTime
import java.util.*

@Service
class TestVariantServiceImpl(

        @Autowired
        private val candidateInfoService: CandidateInfoService,

        @Autowired
        private val testVariantRepository: TestVariantRepository,

        @Autowired
        private val kafkaService: KafkaService,

        @Autowired
        private val taskScheduler: TaskScheduler,

) : TestVariantService,
    ApplicationListener<ApplicationEvent> {

    companion object ClassLogger {
        val logger: Logger = LoggerFactory.getLogger(TestVariantServiceImpl::class.java)
    }

    override fun create(testTemplate: TestTemplate, candidate: CandidateInfo): TestVariant =
            if (testTemplate.eventInfo.testingPhaseState != TestingPhaseState.COMPLETE)
                getByCandidateId(candidate.id)?.run {
                    if (state != TestVariant.TestState.NOT_STARTED) {
                        throw TestStartedException(id, candidateInfo.id)
                    }
                    logger.info("TestVariant for candidate = $candidateInfo already exists. Overwriting...")
                    candidateInfo.also {
                        testVariantRepository.delete(this)
                    }
                }.run {
                    logger.info("Creating TestVariant for $candidate ...")
                    generateTestVariant(this ?: candidate, testTemplate).let {
                        testVariantRepository.save(it)
                    }
                }
            else
                throw TestingPhaseCompleteException(testTemplate.eventInfo.id)

    private fun generateTestVariant(candidate: CandidateInfo, testTemplate: TestTemplate) =
            TestVariant(
                    candidateInfo=candidate,
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
            )

    override fun startTest(testVariantId: UUID): TestVariant = get(testVariantId).run {
            if (testTemplate.eventInfo.testingPhaseState != TestingPhaseState.ACTIVE) {
                throw TestingPhaseNotActiveException(testTemplate.eventInfo.id)
            }
            if (state != TestVariant.TestState.NOT_STARTED) {
                throw TestStartedException(id, candidateInfo.id)
            }
            startedAt = ZonedDateTime.now()
            testVariantRepository.save(this).also {
                taskScheduler.schedule(
                        { onTestTimeExceeded(it) },
                        it.finishingAt!!.toInstant()
                )
            }
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
                        fillInAnswers(question, answerRequestDto.chosenAnswersId)
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

    private fun fillInAnswers(question: Question, chosenAnswerIds: List<UUID>) =
            question.answers.filter {it.id in chosenAnswerIds }.map { AnswerVariant(answer = it) }

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

    private fun onTestTimeExceeded(testVariant: TestVariant) {
        kafkaService.sendCoreEvent(
                TestPassedEvent(
                        emailTo=testVariant.candidateInfo.email,
                        candidateInfo=testVariant.candidateInfo
                )
        )
    }

    override fun onApplicationEvent(event: ApplicationEvent) {
        when (event) {
            is TestTemplateReplaced -> onTestTemplateReplaces(event)
            is TestingPhaseStateChanged -> onTestingPhaseStateChanged(event)
        }
    }

    private fun onTestTemplateReplaces(event: TestTemplateReplaced) =
            candidateInfoService.getAllByEventId(event.template.eventInfo.id).apply {
                logger.info(
                        "Found ${count()} " +
                                "test variants for test template with eventId = ${event.template.eventInfo.id}. " +
                                "Recreating...")
                forEach { create(event.template, it) }
            }

    private fun onTestingPhaseStateChanged(event: TestingPhaseStateChanged) {
        if (event.eventInfo.testingPhaseState == TestingPhaseState.ACTIVE)
            testVariantRepository.findAllByTestTemplateEventInfoId(event.eventInfo.id).run {
                logger.info("Moved testing phase of event = ${event.eventInfo.id} to state ACTIVE.\n" +
                            "Publishing tests, ${count()} test variants found")
                forEach { publishTestVariant(it) }
            }
    }

    private fun publishTestVariant(testVariant: TestVariant) = kafkaService.sendCoreEvent(
            TestCreatedEvent(
                    emailTo=testVariant.candidateInfo.email,
                    testVariant=testVariant
            )
    )

}