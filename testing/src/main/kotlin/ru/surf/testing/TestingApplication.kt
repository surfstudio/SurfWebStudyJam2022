package ru.surf.testing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class TestingApplication

fun main(args: Array<String>) {
    runApplication<TestingApplication>(*args)
}
