package ru.surf.bot.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.kafka.consumer")
data class KafkaConsumerPropertiesConfiguration(
    val clientId: String,
    val bootstrapServers: String,
    val groupId: String,
    val autoOffsetReset: String
)