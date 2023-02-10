package ru.surf.externalfiles

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import ru.surf.externalfiles.configuration.S3PropertiesConfiguration

@SpringBootApplication
@EnableConfigurationProperties(S3PropertiesConfiguration::class)
@EnableScheduling
class ExternalFilesApplication

fun main(args: Array<String>) {
    runApplication<ExternalFilesApplication>(*args)
}
