package ru.surf.meeting.environment

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch

@Component
class KafkaTestConsumer {

    val countDownLatch = CountDownLatch(1)

    lateinit var consumerValue: Any

    @KafkaListener(topics = ["test-topic"])
    fun consume(value: Any) = value.also {
        consumerValue = it
        countDownLatch.countDown()
    }

}