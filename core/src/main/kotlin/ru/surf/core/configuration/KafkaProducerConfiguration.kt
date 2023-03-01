package ru.surf.core.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.kafka.producer")
data class KafkaProducerConfiguration(
    val clientId: String,
    val bootstrapServers: String
)
