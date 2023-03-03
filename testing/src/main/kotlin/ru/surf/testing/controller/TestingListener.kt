package ru.surf.testing.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.surf.core.kafkaEvents.CandidateAppliedEvent
import ru.surf.testing.exception.NoSuchEventException
import ru.surf.testing.mapper.CandidateInfoMapper
import ru.surf.testing.service.TestTemplateService
import ru.surf.testing.service.TestVariantService

@Component
@KafkaListener(topics = ["core-topics"])
class TestingListener(
        @Autowired
        private val testTemplateService: TestTemplateService,

        @Autowired
        private val testVariantService: TestVariantService,

        @Autowired
        private val candidateInfoMapper: CandidateInfoMapper,
) {

    @KafkaHandler
    @RetryableTopic
    fun listenForCandidateAppliedEvent(@Payload candidateAppliedEvent: CandidateAppliedEvent) {
        testVariantService.create(
                testTemplate=testTemplateService.getByEventId(eventId = candidateAppliedEvent.candidate.event.id) ?:
                    throw NoSuchEventException(candidateAppliedEvent.candidate.event.id),
                candidate=candidateInfoMapper.toEntity(candidateAppliedEvent.candidate)
        )
    }

    @KafkaHandler(isDefault = true)
    fun default() = Unit

}
