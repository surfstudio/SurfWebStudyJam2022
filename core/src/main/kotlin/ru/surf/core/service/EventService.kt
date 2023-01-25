package ru.surf.core.service

import ru.surf.core.dto.FullResponseEventDto
import ru.surf.core.dto.PostRequestEventDto
import ru.surf.core.dto.PutRequestEventDto
import ru.surf.core.dto.ShortResponseEventDto
import java.util.UUID

interface EventService {

    fun createEvent(postRequestEventDto: PostRequestEventDto): ShortResponseEventDto

    fun deleteEvent(id: UUID)

    fun getEvent(id: UUID): FullResponseEventDto

    fun updateEvent(id: UUID, putRequestEventDto: PutRequestEventDto): ShortResponseEventDto

}