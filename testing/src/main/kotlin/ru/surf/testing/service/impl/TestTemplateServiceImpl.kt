package ru.surf.testing.service.impl

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import ru.surf.testing.applicationEvents.TestTemplateReplaced
import ru.surf.testing.entity.TestTemplate
import ru.surf.testing.entity.TestingPhaseState
import ru.surf.testing.exception.TestingPhaseActiveException
import ru.surf.testing.repository.TestTemplateRepository
import ru.surf.testing.service.TestTemplateService
import java.util.*

@Service
class TestTemplateServiceImpl(

        @Autowired
        private val testTemplateRepository: TestTemplateRepository,

        @Autowired
        private val applicationEventPublisher: ApplicationEventPublisher,

) : TestTemplateService {

    companion object ClassLogger {
        val logger: Logger = LoggerFactory.getLogger(TestTemplateServiceImpl::class.java)
    }

    override fun create(testTemplate: TestTemplate): TestTemplate =
            getByEventId(testTemplate.eventInfo.id)?.run {
                if (eventInfo.testingPhaseState != TestingPhaseState.PENDING) {
                    throw TestingPhaseActiveException(testTemplate.eventInfo.id)
                }
                logger.info("TestTemplate for ${testTemplate.eventInfo.id} already exists. Overwriting...")
                testTemplateRepository.delete(this)
            }.run {
                logger.info("Creating TestTemplate for ${testTemplate.eventInfo.id}...")
                testTemplateRepository.save(testTemplate).also {
                    this?.apply {
                        applicationEventPublisher.publishEvent(
                                TestTemplateReplaced(
                                        sender=this@TestTemplateServiceImpl,
                                        template=it
                                )
                        )
                    }
                }
            }

    override fun getByEventId(eventId: UUID): TestTemplate? =
            testTemplateRepository.findByEventInfoId(eventId)

}