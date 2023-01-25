package ru.surf.core.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.surf.core.entity.EventType
import ru.surf.core.repository.EventTypeRepository
import ru.surf.core.service.EventTypeService
import java.util.*

@Service
class EventTypeServiceImpl(private val eventTypeRepository: EventTypeRepository) : EventTypeService {

    override fun getEventType(id: UUID): EventType =
        eventTypeRepository.findByIdOrNull(id) ?: throw NoSuchElementException()

}