package ru.surf.core.kafkaEvents.meeting

import ru.surf.core.entity.SurfEmployee
import ru.surf.core.entity.Trainee
import ru.surf.meeting.enumeration.DefenceType
import java.time.LocalDateTime

class CancelDefenceMeetingEvent(
    override val title: String,
    override val date: LocalDateTime,
    val zoomMeetingId: Long,
    override val duration: Int = 0,
    val defenceType: DefenceType = DefenceType.DEFENCE_CANCELLED,
    override val description: String,
    override val surfParticipants: List<SurfEmployee>,
    val traineeParticipants: List<Trainee>
) : MeetingEvent {
}