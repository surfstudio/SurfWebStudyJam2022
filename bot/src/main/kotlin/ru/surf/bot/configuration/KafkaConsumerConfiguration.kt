package ru.surf.bot.configuration

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.kafka.support.converter.ByteArrayJsonMessageConverter
import org.springframework.kafka.support.converter.JsonMessageConverter
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.util.backoff.FixedBackOff

@Configuration
@EnableKafka
class KafkaConsumerConfiguration(private val kafkaConsumerPropertiesConfiguration: KafkaConsumerPropertiesConfiguration) {

    @Bean
    fun jsonMessageConverter(): JsonMessageConverter {
        return ByteArrayJsonMessageConverter()
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String, Any> {
        return DefaultKafkaConsumerFactory(
            mapOf(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaConsumerPropertiesConfiguration.bootstrapServers,
                ConsumerConfig.CLIENT_ID_CONFIG to kafkaConsumerPropertiesConfiguration.clientId,
                ConsumerConfig.GROUP_ID_CONFIG to kafkaConsumerPropertiesConfiguration.groupId,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to kafkaConsumerPropertiesConfiguration.autoOffsetReset
            ),
            StringDeserializer(),
            JsonDeserializer<Any>().apply {
                addTrustedPackages("ru.surf.core.*")
            }
        )
    }

    @Bean
    fun errorHandler(): DefaultErrorHandler =
        DefaultErrorHandler(FixedBackOff())

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Any> =
        ConcurrentKafkaListenerContainerFactory<String, Any>().apply {
            consumerFactory = consumerFactory()
            setCommonErrorHandler(errorHandler())
        }

}
