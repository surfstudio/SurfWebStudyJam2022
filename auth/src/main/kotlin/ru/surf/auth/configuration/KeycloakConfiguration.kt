package ru.surf.auth.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding


@ConfigurationProperties(prefix = "application.keycloak")
@ConstructorBinding
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
