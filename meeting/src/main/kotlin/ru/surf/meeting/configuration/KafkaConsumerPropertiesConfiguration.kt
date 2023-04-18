package ru.surf.meeting.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.kafka.consumer")
class KafkaConsumerPropertiesConfiguration(
    val clientId: String,
    val bootstrapServers: String,
    val groupId: String,
    val autoOffsetReset: String
)