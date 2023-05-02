package ru.surf.meeting.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer


@Configuration
class KafkaProducerConfiguration(private val kafkaProducerPropertiesConfiguration: KafkaProducerPropertiesConfiguration) {

    object TOPICS {
        const val CORE_TOPICS: String = "core-topics"
    }

    @Bean
    fun kafkaAdmin(): KafkaAdmin =
        KafkaAdmin(
            mapOf(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProducerPropertiesConfiguration.bootstrapServers,
                AdminClientConfig.CLIENT_ID_CONFIG to kafkaProducerPropertiesConfiguration.clientId,
            )
        )

    @Bean
    fun producerFactory(): ProducerFactory<String, Any> = DefaultKafkaProducerFactory(
        mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProducerPropertiesConfiguration.bootstrapServers,
            ProducerConfig.CLIENT_ID_CONFIG to kafkaProducerPropertiesConfiguration.clientId,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java,
            ProducerConfig.ACKS_CONFIG to "all"
        )
    )

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, Any> {
        return KafkaTemplate(producerFactory())
    }

    @Bean
    fun coreTopic() =
        TopicBuilder.name(TOPICS.CORE_TOPICS)
            .partitions(3)
            .replicas(1)
            .build()

}
