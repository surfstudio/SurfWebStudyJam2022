package ru.surf.testing.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*
import org.springframework.kafka.support.converter.ByteArrayJsonMessageConverter
import org.springframework.kafka.support.converter.JsonMessageConverter
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer


@Configuration
@EnableKafka
class KafkaConfiguration(

        @Autowired
        private val objectMapper: ObjectMapper,

) {

    @Bean
    fun jsonMessageConverter(): JsonMessageConverter {
        return ByteArrayJsonMessageConverter()
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String, Any> {
        return DefaultKafkaConsumerFactory(
                mapOf(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "kafka-service:9092",
                        ConsumerConfig.CLIENT_ID_CONFIG to "testing_service",
                        ConsumerConfig.GROUP_ID_CONFIG to "1",
                        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to "StringDeserializer::class.java",
                        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to "JsonDeserializer::class.java",
                        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest"
                ),
                StringDeserializer(),
                JsonDeserializer<Any>().apply {
                    addTrustedPackages("ru.surf.core.kafkaEvents")
                }
        )
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Any> =
            ConcurrentKafkaListenerContainerFactory<String, Any>().apply {
                consumerFactory = consumerFactory()
            }

    @Bean
    fun producerFactory(): ProducerFactory<String, Any> = DefaultKafkaProducerFactory(mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "kafka-service:9092",
            ProducerConfig.CLIENT_ID_CONFIG to "testing_service",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java,
    ), StringSerializer(), JsonSerializer(objectMapper))

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, Any> {
        return KafkaTemplate(producerFactory())
    }

}