package ru.surf.core.kafkaEvents.meeting

import ru.surf.core.entity.SurfEmployee
import java.time.LocalDateTime

sealed interface MeetingEvent {
    val title: String
    val description: String
    val duration: Int
    val date: LocalDateTime
    val surfParticipants: List<SurfEmployee>
}