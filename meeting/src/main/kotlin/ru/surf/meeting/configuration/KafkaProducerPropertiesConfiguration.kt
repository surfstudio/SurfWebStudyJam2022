package ru.surf.meeting.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.kafka.producer")
data class KafkaProducerPropertiesConfiguration(
    val clientId: String,
    val bootstrapServers: String
)
