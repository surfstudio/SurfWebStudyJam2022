package ru.surf.meeting


import org.apache.kafka.clients.admin.AdminClient
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import org.testcontainers.junit.jupiter.Testcontainers
import ru.surf.meeting.environment.KafkaTestConsumer
import ru.surf.meeting.environment.KafkaTestProducer
import java.util.concurrent.TimeUnit


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class KafkaIntegrationTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var kafkaAdmin: KafkaAdmin

    @Autowired
    private lateinit var testProducer: KafkaTestProducer

    @Autowired
    private lateinit var testConsumer: KafkaTestConsumer


    @Test
    fun `test topic creation when container init`() {
        val adminClient = AdminClient.create(kafkaAdmin.configurationProperties)
        val topicList = adminClient.listTopics().listings().get()
        Assertions.assertNotNull(topicList)
        Assertions.assertTrue(!topicList.isEmpty())
    }

    @Test
    fun `test kafka producer not throw exception`() {
        assertDoesNotThrow { testProducer.send(testTopic, "test-value") }
    }

}
