package ru.surf.mail

import com.icegreen.greenmail.configuration.GreenMailConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.extension.RegisterExtension
import com.icegreen.greenmail.junit5.GreenMailExtension
import org.junit.jupiter.api.Assertions.assertEquals
import com.icegreen.greenmail.util.ServerSetup
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import ru.surf.core.kafkaEvents.EmailType
import ru.surf.core.kafkaEvents.GeneralNotificationDto

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MailApplicationTests {

    companion object {
        @RegisterExtension
        var greenMail: GreenMailExtension = GreenMailExtension(ServerSetup.SMTP.withPort(3025)).withConfiguration(
            GreenMailConfiguration.aConfig().withUser("user", "admin")
        )

        @JvmStatic
        @DynamicPropertySource
        fun kafkaProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.kafka.producer.bootstrap-servers") { KafkaBase.kafkaContainer.bootstrapServers }
            registry.add("spring.kafka.bootstrap-servers") { KafkaBase.kafkaContainer.bootstrapServers }
        }

        @JvmStatic
        @BeforeAll
        fun setup() {
            KafkaBase.createTopic("core-topics")
        }
    }

    @Test
    fun `should not send notification`() {
        KafkaBase.writeToTopic("Test", "core-topics")

        Thread.sleep(2000)

        assertTrue(greenMail.receivedMessages.isEmpty())
    }

    @Test
    fun `should send accept application notification`() {
        val email = GeneralNotificationDto(
            EmailType.ACCEPT_APPLICATION,
            "test_mail@surf.ru",
            "Accept application Test",
            mapOf("firstName" to "FirstName", "lastName" to "LastName", "eventName" to "EventName")
        )
        KafkaBase.writeToTopic(email, "core-topics")

        Thread.sleep(2000)

        assertEquals(email.subject, greenMail.receivedMessages.first().subject)
    }
}
