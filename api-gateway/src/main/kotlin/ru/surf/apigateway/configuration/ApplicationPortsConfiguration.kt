package ru.surf.apigateway.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "application.ports")
data class ApplicationPortsConfiguration(
        val serviceCorePort: String,
        val externalFilesPort: String,
        val serviceTestingPort: String,
)
