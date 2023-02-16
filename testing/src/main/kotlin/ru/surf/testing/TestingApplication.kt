package ru.surf.testing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class TestingApplication

fun main(args: Array<String>) {
    runApplication<TestingApplication>(*args)
}
