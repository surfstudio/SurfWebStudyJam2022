package ru.surf.mail.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.surf.core.kafkaEvents.IMailEvent

@Component
@KafkaListener(topics = ["core-topics"])
class EmailListener {

    companion object EmailListener {
        val logger: Logger = LoggerFactory.getLogger(EmailListener::class.java)
    }

    @KafkaHandler
    fun listenForMailEvent(@Payload value: IMailEvent) {
        logger.info("Received mail event ${value::class.qualifiedName} with email=${value.emailTo}, params=[${value.convertToParam()}]")
    }

    @KafkaHandler(isDefault = true)
    fun default() = Unit

}