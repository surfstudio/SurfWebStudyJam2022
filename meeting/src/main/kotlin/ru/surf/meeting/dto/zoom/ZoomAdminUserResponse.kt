package ru.surf.meeting.dto.zoom

import com.fasterxml.jackson.annotation.JsonProperty

data class ZoomAdminUserResponse(
    @JsonProperty("id") val id: String,
    @JsonProperty("first_name") val firstName: String,
    @JsonProperty("last_name") val lastName: String,
    @JsonProperty("display_name") val displayName: String,
    @JsonProperty("email") val email: String,
    @JsonProperty("type") val userPlanType: Int,
    @JsonProperty("role_name") val role: String,
    @JsonProperty("timezone") val timeZone: String,
)
