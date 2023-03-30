package ru.surf.core.service

import ru.surf.core.dto.PostRequestEventDto
import ru.surf.core.dto.PutRequestEventDto
import ru.surf.core.dto.ShortResponseEventDto
import ru.surf.core.entity.Event
import java.util.*

interface EventService {

    fun createEvent(postRequestEventDto: PostRequestEventDto): ShortResponseEventDto

    fun deleteEvent(id: UUID)

    fun getEvent(id: UUID): Event

    fun updateEvent(id: UUID, putRequestEventDto: PutRequestEventDto): ShortResponseEventDto

    fun getReport(id: UUID): ByteArray

}