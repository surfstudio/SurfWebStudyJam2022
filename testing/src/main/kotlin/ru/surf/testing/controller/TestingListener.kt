package ru.surf.testing.controller

import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.surf.core.kafkaEvents.CandidateAppliedEvent

@Component
@KafkaListener(topics = ["core-topics"])
class TestingListener {

    @KafkaHandler
    fun listenForCandidateAppliedEvent(@Payload value: CandidateAppliedEvent) {
        //TODO do some job
    }

    @KafkaHandler(isDefault = true)
    fun default() = Unit

}