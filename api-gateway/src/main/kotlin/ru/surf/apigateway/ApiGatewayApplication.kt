package ru.surf.apigateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import ru.surf.apigateway.configuration.ApplicationPortsConfiguration

@EnableConfigurationProperties(ApplicationPortsConfiguration::class)
@SpringBootApplication
class ApiGatewayApplication

fun main(args: Array<String>) {
    runApplication<ApiGatewayApplication>(*args)
}
