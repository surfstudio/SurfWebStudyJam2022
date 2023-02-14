package ru.surf.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import ru.surf.auth.configuration.KeycloakConfiguration


@SpringBootApplication
@EnableConfigurationProperties(KeycloakConfiguration::class)
class AuthServiceApplication

fun main(args: Array<String>) {
    runApplication<AuthServiceApplication>(*args)
}
