package ru.surf.mail.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.surf.core.kafkaEvents.MailEvent
import ru.surf.mail.service.EmailService

@Component
@KafkaListener(topics = ["core-topics"])
class EmailListener(
    val emailService: EmailService
) {

    companion object EmailListener {
        val logger: Logger = LoggerFactory.getLogger(EmailListener::class.java)
    }

    @KafkaHandler
    @RetryableTopic
    fun listenForMailEvent(@Payload value: MailEvent) {
        emailService.sendEmail(value)
        logger.info("Sent mail event ${value::class.qualifiedName} " +
                    "to ${value.emailTo} " +
                    "with subject=${value.subject}, params=[${value.params()}]")
    }

    @KafkaHandler(isDefault = true)
    fun default() = Unit

}