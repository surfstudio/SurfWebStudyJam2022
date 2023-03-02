package ru.surf.testing.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.surf.core.kafkaEvents.CandidateAppliedEvent
import ru.surf.testing.service.TestTemplateService
import ru.surf.testing.service.TestVariantService

@Component
@KafkaListener(topics = ["core-topics"])
class TestingListener(
        @Autowired
        private val testTemplateService: TestTemplateService,

        @Autowired
        private val testVariantService: TestVariantService,
) {

    companion object ClassLogger {
        val logger: Logger = LoggerFactory.getLogger(TestingListener::class.java)
    }

    @KafkaHandler
    fun listenForCandidateAppliedEvent(@Payload candidateAppliedEvent: CandidateAppliedEvent) {
        testVariantService.create(
                testTemplate=testTemplateService.getByEventId(eventId = candidateAppliedEvent.candidate.event.id) ?:
                throw NoSuchElementException(candidateAppliedEvent.candidate.event.id.toString()),
                candidateId=candidateAppliedEvent.candidate.id
        ).also {
            logger.info("Generated testTemplate (id=${it.id}) for candidate (id=${it.candidateInfo.id})")
        }
    }

    @KafkaHandler(isDefault = true)
    fun default() = Unit

}
