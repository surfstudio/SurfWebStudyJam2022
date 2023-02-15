package ru.surf.core.configuration

import org.apache.kafka.clients.admin.AdminClientConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaTopicConfiguration(private val kafkaProducerConfiguration: KafkaProducerConfiguration) {

    object TOPICS {
        const val RECEIVING_REQUEST_TOPIC: String = "receiving-request-topic"
    }

    @Bean
    fun kafkaAdmin(): KafkaAdmin =
        KafkaAdmin(
            mapOf(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProducerConfiguration.bootstrapServers,
                AdminClientConfig.CLIENT_ID_CONFIG to kafkaProducerConfiguration.clientId
            )
        )

    @Bean
    fun notificationTopic() =
        TopicBuilder.name(TOPICS.RECEIVING_REQUEST_TOPIC)
            .partitions(3)
            .replicas(1)
            .build()

}