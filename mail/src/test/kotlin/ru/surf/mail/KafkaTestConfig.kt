package ru.surf.mail

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.converter.ByteArrayJsonMessageConverter
import org.springframework.kafka.support.converter.JsonMessageConverter
import org.springframework.kafka.support.serializer.JsonDeserializer

@TestConfiguration
class KafkaTestConfig {
    @Bean
    fun jsonMessageConverter(): JsonMessageConverter {
        return ByteArrayJsonMessageConverter()
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String, Any> {
        println("FOOOOOOOOOOO")
        return DefaultKafkaConsumerFactory(
            mapOf(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to KafkaBase.kafkaContainer.bootstrapServers,
                ConsumerConfig.CLIENT_ID_CONFIG to "mail_service",
                ConsumerConfig.GROUP_ID_CONFIG to "2",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to "StringDeserializer::class.java",
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to "JsonDeserializer::class.java",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest"
            ),
            StringDeserializer(),
            JsonDeserializer<Any>().apply {
                addTrustedPackages("ru.surf.*")
            }
        )
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Any> =
        ConcurrentKafkaListenerContainerFactory<String, Any>().apply {
            consumerFactory = consumerFactory()
        }
}