package ru.surf.meeting.listener

import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.surf.core.kafkaEvents.meeting.MeetingEvent
import ru.surf.meeting.service.StrategyService

@Component
@KafkaListener(topics = ["core-topics"])
class MeetingListener(
    private val strategyService: StrategyService
) {

    @KafkaHandler
    @RetryableTopic
    fun listenDefenceMeetingEvent(@Payload event: MeetingEvent) = strategyService.chooseStrategy(event)

    @KafkaHandler(isDefault = true)
    fun default() = Unit

}