package ru.surf.bot.listener

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.surf.bot.service.BotService
import ru.surf.core.kafkaEvents.bot.BotEvent

@Component
@KafkaListener(topics = ["core-topics"])
class KafkaBotListener(private val botService: BotService) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ru.surf.bot.listener.KafkaBotListener::class.java)
    }

    @KafkaHandler
    @RetryableTopic
    fun listenForBotEvent(@Payload botEvent: BotEvent) {
        botService.sendGeneralNotification(botEvent)
        logger.info(
            "Sent bot event ${botEvent::class.qualifiedName} " + " params=[${botEvent.params()}]"
        )
    }

    @KafkaHandler(isDefault = true)
    fun default() = Unit

}