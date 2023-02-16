package ru.surf.mail.controller

import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.surf.core.kafkaEvents.IMailEvent

@Component
@KafkaListener(topics = ["core-topics"])
class EmailListener {

    @KafkaHandler
    fun listenForMailEvent(@Payload value: IMailEvent) {
        //TODO do some job
        //emailService.sendGreeting(value)
    }

    @KafkaHandler(isDefault = true)
    fun default() = Unit

}