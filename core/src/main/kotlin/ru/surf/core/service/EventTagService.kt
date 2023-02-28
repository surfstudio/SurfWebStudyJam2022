package ru.surf.core.service

import ru.surf.core.entity.EventTag
import java.util.UUID

interface EventTagService {

    fun getEventTag(id: UUID): EventTag

}