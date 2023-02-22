package ru.surf.mail

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class MailApplication

fun main(args: Array<String>) {
    runApplication<MailApplication>(*args)
}
