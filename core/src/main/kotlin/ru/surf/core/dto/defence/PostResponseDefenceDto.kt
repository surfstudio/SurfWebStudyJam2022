package ru.surf.core.dto.defence

import ru.surf.core.entity.SurfEmployee
import ru.surf.core.entity.Trainee
import java.time.LocalDateTime
import java.util.UUID

data class PostResponseDefenceDto(
        val id:UUID,
        val zoomMeetingId: Long,
        val title: String,
        val description: String,
        val date: LocalDateTime,
        val candidatesParticipants: List<Trainee>,
        var employeeParticipants: List<SurfEmployee>,
        var zoomLink: String
)
