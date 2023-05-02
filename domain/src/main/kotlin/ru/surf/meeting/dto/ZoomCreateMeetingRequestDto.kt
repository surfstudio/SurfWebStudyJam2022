package ru.surf.meeting.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder(
    *["agenda", "duration", "recurrence", "host_video", "private_meeting",
    "start_time", "topic"])
data class ZoomCreateMeetingRequestDto(
        @JsonProperty("duration") val duration: Int,
        @JsonProperty("agenda") val description: String,
        @JsonProperty("host_video") val hostVideo: Boolean = false,
        @JsonProperty("private_meeting") val privateMeeting: Boolean = false,
        @JsonProperty("start_time") val startTime: String,
        @JsonProperty("topic") val topic: String,
        @JsonProperty("recurrence") val recurrence: Recurrence
):java.io.Serializable {
    data class Recurrence(
        @JsonProperty("type") val type: Int = 2
    ):java.io.Serializable
}
