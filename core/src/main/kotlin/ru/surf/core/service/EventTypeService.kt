package ru.surf.core.service

import ru.surf.core.entity.EventType
import java.util.UUID

interface EventTypeService {

    fun getEventType(id: UUID): EventType

}