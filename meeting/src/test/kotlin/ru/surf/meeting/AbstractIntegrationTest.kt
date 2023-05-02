package ru.surf.meeting

import org.apache.kafka.clients.admin.AdminClientConfig
import org.junit.jupiter.api.BeforeAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AbstractIntegrationTest {

    companion object TestKafka {

        @Container
        val testKafka = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest")).apply {
            withEmbeddedZookeeper()
        }

        const val testTopic = "test-topic"

        @JvmStatic
        @BeforeAll
        internal fun initialTopics(@Autowired kafkaAdmin: KafkaAdmin) =
            kafkaAdmin.createOrModifyTopics(TopicBuilder.name(testTopic).partitions(3).build())


        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) = apply {
            registry.add("spring.kafka.producer.bootstrap-servers", testKafka::getBootstrapServers)
            registry.add("spring.kafka.producer.client-id") { "test-producer" }
            registry.add("spring.kafka.consumer.client-id") { "test-consumer" }
        }

    }

    class TestConfig {
        @Bean
        fun testKafkaAdmin() =
            KafkaAdmin(mapOf(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to testKafka.bootstrapServers))

    }

}