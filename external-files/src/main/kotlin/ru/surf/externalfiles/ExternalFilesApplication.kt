package ru.surf.externalfiles

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.util.DigestUtils
import ru.surf.externalfiles.configuration.S3PropertiesConfiguration

@EnableConfigurationProperties(S3PropertiesConfiguration::class)
@SpringBootApplication
class ExternalFilesApplication

fun main(args: Array<String>) {
    runApplication<ExternalFilesApplication>(*args)
}
