package ru.surf.meeting.dto.zoom

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.util.*

@JsonPropertyOrder(
    *["host_email", "id", "agenda", "created_at", "duration",
        "join_url", "host_video", "private_meeting", "start_time", "start_url","timezone", "topic", "type"]
)
data class ZoomCreateMeetingResponseDto(
        @JsonProperty("host_email") val hostEmail: String,
        @JsonProperty("id") val id: Long,
        @JsonProperty("agenda") val description: String,
        @JsonProperty("created_at") val createdAt: Any,
        @JsonProperty("duration") val duration: Int,
        @JsonProperty("join_url") val joinUrl: String,
        @JsonProperty("host_video") val hostVideo: Boolean,
        @JsonProperty("private_meeting") val privateMeeting: Boolean,
        @JsonProperty("start_time") val startTime: Any,
        @JsonProperty("start_url") val startHostUrl: String,
        @JsonProperty("topic") val topic: String,
        @JsonProperty("type") val type: Int,
        @JsonProperty("timezone") val timezone: String,
):java.io.Serializable