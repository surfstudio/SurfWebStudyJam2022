package ru.surf.core.service.impl

import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.KafkaException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaProducerException
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.surf.core.configuration.KafkaProducerConfiguration
import ru.surf.core.exception.ExceptionType
import ru.surf.core.service.KafkaService

@Service
class KafkaServiceImpl(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
) : KafkaService {

    companion object KafkaLogger {
        val logger: Logger = LoggerFactory.getLogger(KafkaServiceImpl::class.java)
    }

    override fun sendCoreEvent(event: Any) {
        val requestKafkaEventRecord =
            ProducerRecord<String, Any>(
                KafkaProducerConfiguration.TOPICS.CORE_TOPICS,
                event
            )
        kafkaTemplate.send(requestKafkaEventRecord).whenComplete { result, ex ->
            when (ex == null) {
                true -> logger.info("Successfully send $event to ${KafkaProducerConfiguration.TOPICS.CORE_TOPICS}")
                false -> {
                    logger.info("Message sending failed with data $result")
                    throw KafkaProducerException(
                        requestKafkaEventRecord,
                        ExceptionType.SERVICE_EXCEPTION.toString(),
                        KafkaException()
                    )
                }
            }
        }
    }

}
