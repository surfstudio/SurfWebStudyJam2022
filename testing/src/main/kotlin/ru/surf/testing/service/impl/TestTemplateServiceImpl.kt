package ru.surf.testing.service.impl

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import ru.surf.testing.applicationEvents.TestTemplateReplaced
import ru.surf.testing.entity.TestTemplate
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
            getByEventId(testTemplate.eventId)?.let {
                logger.info("TestTemplate for ${testTemplate.eventId} already exists. Overwriting...")
                testTemplateRepository.delete(it)
            }.run {
                logger.info("Creating TestTemplate for ${testTemplate.eventId}...")
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
            testTemplateRepository.findByEventId(eventId)

}