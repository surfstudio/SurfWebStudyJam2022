package ru.surf.core.service

import ru.surf.core.entity.EventTag
import java.util.UUID

interface EventTypeService {

    fun getEventType(id: UUID): EventTag

}