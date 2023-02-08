package ru.surf.core.configuration

import org.apache.kafka.clients.admin.AdminClientConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaTopicConfiguration(private val kafkaProducerConfiguration: KafkaProducerConfiguration) {

    object TopicConfiguration {
        const val notificationTopic: String = "notification-topic"
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
        TopicBuilder.name(TopicConfiguration.notificationTopic)
            .partitions(3)
            .replicas(1)
            .build()

}