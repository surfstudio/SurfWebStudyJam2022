package ru.surf.bot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import ru.surf.bot.configuration.BotConfigurationProperties
import ru.surf.bot.configuration.KafkaConsumerPropertiesConfiguration

@EnableConfigurationProperties(BotConfigurationProperties::class, KafkaConsumerPropertiesConfiguration::class)
@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class BotApplication

fun main(args: Array<String>) {
    runApplication<BotApplication>(*args)
}
