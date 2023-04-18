package ru.surf.core.kafkaEvents.meeting

import ru.surf.core.entity.SurfEmployee
import ru.surf.core.entity.Trainee
import ru.surf.meeting.enumeration.DefenceType
import ru.surf.meeting.dto.ZoomCreateMeetingRequestDto
import java.time.LocalDateTime

data class CreateDefenceMeetingEvent(
    override val title: String,
    override val description: String,
    override val date: LocalDateTime,
    override val duration: Int,
    val eventName: String,
    val candidateParticipants: List<Trainee>,
    override val surfParticipants: List<SurfEmployee>,
    val zoomLink: String,
    val type: DefenceType = DefenceType.DEFENCE_CREATED
) : MeetingEvent {
}