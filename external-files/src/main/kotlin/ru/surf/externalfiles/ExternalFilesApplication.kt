package ru.surf.externalfiles

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExternalFilesApplication

fun main(args: Array<String>) {
    runApplication<ExternalFilesApplication>(*args)
}
