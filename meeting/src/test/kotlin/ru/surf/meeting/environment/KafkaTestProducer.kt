package ru.surf.meeting.environment

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaTestProducer {

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, Any>

    fun send(topic: String, value: Any) = kafkaTemplate.send(topic, value)

}