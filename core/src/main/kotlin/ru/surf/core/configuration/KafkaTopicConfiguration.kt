package ru.surf.core.configuration

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
class KafkaTopicConfiguration(

        @Autowired
        private val kafkaProducerConfiguration: KafkaProducerConfiguration,

        @Autowired
        private val objectMapper: ObjectMapper,

) {

    object TOPICS {
        const val CORE_TOPICS: String = "core-topics"
    }

    @Bean
    fun kafkaAdmin(): KafkaAdmin =
            KafkaAdmin(
                    mapOf(
                            AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProducerConfiguration.bootstrapServers,
                            AdminClientConfig.CLIENT_ID_CONFIG to kafkaProducerConfiguration.clientId,
                    )
            )

    @Bean
    fun producerFactory(): ProducerFactory<String, Any> = DefaultKafkaProducerFactory(mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProducerConfiguration.bootstrapServers,
            ProducerConfig.CLIENT_ID_CONFIG to kafkaProducerConfiguration.clientId,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java,
    ), StringSerializer(), JsonSerializer(objectMapper))

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
