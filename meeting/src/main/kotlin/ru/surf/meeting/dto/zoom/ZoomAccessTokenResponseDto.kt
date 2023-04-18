package ru.surf.meeting.dto.zoom

import com.fasterxml.jackson.annotation.JsonProperty

data class ZoomAccessTokenResponseDto(
    @JsonProperty("access_token") val accessToken: String,
    @JsonProperty("token_type") val tokenType: String,
    @JsonProperty("expires_in") val expiresIn: Long,
    @JsonProperty("scope") val scope: String,
) {
    val expiresInMinutes: Int
        get() = (expiresIn % 3600).div(60).toInt()

    override fun toString(): String {
        return "ZoomAccessTokenResponseDto(accessToken='$accessToken', tokenType='$tokenType', expiresIn=$expiresIn, scope='$scope')"
    }
}

