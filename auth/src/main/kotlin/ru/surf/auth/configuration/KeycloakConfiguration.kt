package ru.surf.auth.configuration

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "application.keycloak")
data class KeycloakConfiguration(
        val certsId: String,

        val clientId: String,

        val clientSecret: String,

        val clientGrantType: String,

        val userGrantType: String,

        val certsUrl: String,

        val tokenUrl: String,

        val userInfoUrl: String,

        val usersUrl: String,

        val resetPasswordUrl: String,
)
