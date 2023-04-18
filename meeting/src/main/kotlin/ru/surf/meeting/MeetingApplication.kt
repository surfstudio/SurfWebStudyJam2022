package ru.surf.meeting


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.retry.annotation.EnableRetry
import org.springframework.scheduling.annotation.EnableScheduling
import ru.surf.meeting.configuration.KafkaConsumerPropertiesConfiguration
import ru.surf.meeting.configuration.KafkaProducerPropertiesConfiguration
import ru.surf.meeting.configuration.ZoomAdminPropertiesConfiguration
import ru.surf.meeting.configuration.ZoomPropertiesConfiguration

@EnableConfigurationProperties(
    ZoomPropertiesConfiguration::class,
    KafkaProducerPropertiesConfiguration::class,
    KafkaConsumerPropertiesConfiguration::class,
    ZoomAdminPropertiesConfiguration::class
)
@EnableScheduling
@EnableRetry
@SpringBootApplication
class MeetingApplication

fun main(args: Array<String>) {
    runApplication<MeetingApplication>(*args)
}