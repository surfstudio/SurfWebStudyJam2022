package ru.surf.core.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.surf.core.entity.EventTag
import ru.surf.core.repository.EventTagRepository
import ru.surf.core.service.EventTagService
import java.util.*

@Service
class EventTagServiceImpl(private val eventTagRepository: EventTagRepository) : EventTagService {

    override fun getEventTag(id: UUID): EventTag =
        eventTagRepository.findByIdOrNull(id) ?: throw NoSuchElementException()

}