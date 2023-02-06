package ru.surf.core

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import ru.surf.core.configuration.KafkaProducerConfiguration

@EnableConfigurationProperties(KafkaProducerConfiguration::class)
@SpringBootApplication
class CoreApplication

fun main(args: Array<String>) {
    runApplication<CoreApplication>(*args)
}
