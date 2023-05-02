package ru.surf.meeting.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "zoom.admin")
data class ZoomAdminPropertiesConfiguration(
    val adminId: String,
    val adminReserveId: String
) {
    var authorizationBasicToken: String = ""
    var authorizationBearerToken: String = ""
}