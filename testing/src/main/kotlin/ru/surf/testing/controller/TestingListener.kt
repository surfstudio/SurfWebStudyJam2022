package ru.surf.testing.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.surf.core.kafkaEvents.CandidateAppliedEvent
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
    fun listenForCandidateAppliedEvent(@Payload candidateAppliedEvent: CandidateAppliedEvent) {
        testVariantService.create(
                testTemplate=testTemplateService.getByEventId(eventId = candidateAppliedEvent.candidate.event.id) ?:
                    throw NoSuchElementException(candidateAppliedEvent.candidate.event.id.toString()),
                candidate=candidateInfoMapper.toEntity(candidateAppliedEvent.candidate)
        )
    }

    @KafkaHandler(isDefault = true)
    fun default() = Unit

}
