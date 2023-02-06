package ru.surf.core.service.impl

import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.KafkaException
import org.springframework.kafka.core.KafkaProducerException
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.surf.core.dto.GeneralNotificationDto
import ru.surf.core.exception.ExceptionType
import ru.surf.core.service.KafkaService

@Service
class KafkaServiceImpl(
    private val kafkaTemplate: KafkaTemplate<String, GeneralNotificationDto>,
) : KafkaService {

    companion object TOPICS {
        const val NOTIFICATION_TOPIC: String = "notification-topic"
    }

    override fun sendGeneralNotificationMessage(generalNotificationDto: GeneralNotificationDto) {
        val notificationRecord =
            ProducerRecord<String, GeneralNotificationDto>(NOTIFICATION_TOPIC, generalNotificationDto)
        //TODO: Позже добавить детальную обработку
        kafkaTemplate.send(notificationRecord).completable().whenComplete { result, ex ->
            when (ex == null) {
                true -> println("Message success send to topic $NOTIFICATION_TOPIC")
                false -> {
                    println("Message sending failed with data $result")
                    throw KafkaProducerException(
                        notificationRecord,
                        ExceptionType.SERVICE_EXCEPTION.toString(),
                        KafkaException()
                    )
                }
            }
        }
    }

}