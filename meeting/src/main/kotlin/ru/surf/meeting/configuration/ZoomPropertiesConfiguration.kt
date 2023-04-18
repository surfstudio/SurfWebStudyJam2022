package ru.surf.meeting.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "zoom")
data class ZoomPropertiesConfiguration(
    val apiUrl: String,
    val baseUrl: String,
    val accountId: String,
    val clientId: String,
    val clientSecret: String
)
