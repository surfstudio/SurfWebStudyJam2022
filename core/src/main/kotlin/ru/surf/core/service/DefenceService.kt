package ru.surf.core.service

import ru.surf.core.dto.defence.PostRequestDefenceDto
import ru.surf.core.dto.defence.PostResponseDefenceDto
import ru.surf.core.entity.Defence
import java.util.UUID

interface DefenceService {

    fun createDefence(postRequestDefenceDto: PostRequestDefenceDto): PostResponseDefenceDto

    fun findDefence(id: UUID): Defence

    fun deleteDefence(id: UUID)

    fun deleteDefenceByMeetingId(zoomMeetingId: Long)

    fun findDefenceByMeetingId(zoomMeetingId: Long): Defence

}