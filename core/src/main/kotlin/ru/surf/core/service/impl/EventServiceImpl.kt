package ru.surf.core.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.surf.core.dto.FullResponseEventDto
import ru.surf.core.dto.PostRequestEventDto
import ru.surf.core.dto.PutRequestEventDto
import ru.surf.core.dto.ShortResponseEventDto
import ru.surf.core.exception.event.EventNotFoundByIdException
import ru.surf.core.mapper.event.EventMapper
import ru.surf.core.repository.EventRepository
import ru.surf.core.service.EventService
import ru.surf.core.service.EventTypeService
import ru.surf.core.service.SurfEmployeeService
import java.util.*


@Service
class EventServiceImpl(
        private val eventMapper: EventMapper,
        private val eventRepository: EventRepository,
        private val eventTypeService: EventTypeService,
        private val surfEmployeeService: SurfEmployeeService,
) : EventService {

    override fun createEvent(postRequestEventDto: PostRequestEventDto): ShortResponseEventDto {
        val initiatorId = postRequestEventDto.eventInitiatorId
        val eventTagsId = postRequestEventDto.eventTagsId
        // TODO заглушка, убрать nullable когда будут готовы сервисы для связанных сущностей
        val transientEntity = eventMapper.convertFromPostRequestEventDtoToEventEntity(postRequestEventDto).apply {
            eventInitiator = initiatorId?.let { surfEmployeeService.getSurfEmployee(it) }
            eventTags = eventTagsId.map { eventTypeService.getEventType(it) }
        }
        val persistedEvent = eventRepository.save(transientEntity)
        return eventMapper.convertFromEventEntityToShortResponseEventDto(persistedEvent)
    }

    override fun getEvent(id: UUID): FullResponseEventDto {
        val eventFromDb = getEventFromDatabase(id)
        return eventMapper.convertFromEventEntityToFullResponseEventDto(eventFromDb)
    }

    override fun updateEvent(id: UUID, putRequestEventDto: PutRequestEventDto): ShortResponseEventDto {
        val eventFromDb = getEventFromDatabase(id)
        eventFromDb.apply {
            title = putRequestEventDto.title
            description = putRequestEventDto.description
            candidatesAmount = putRequestEventDto.candidatesAmount
            offersAmount = putRequestEventDto.offersAmount
            traineesAmount = putRequestEventDto.traineesAmount
            eventTags = putRequestEventDto.eventTagsId.map { eventTypeService.getEventType(it) }
            eventInitiator = surfEmployeeService.getSurfEmployee(putRequestEventDto.eventInitiatorId)
            // TODO: 25.01.2023 Добавить логику потом 
            /*  statesEvents = TODO()*/
        }
        val persistedEvent = eventRepository.save(eventFromDb)
        return eventMapper.convertFromEventEntityToShortResponseEventDto(persistedEvent)
    }

    override fun deleteEvent(id: UUID) {
        val eventFromDb = getEventFromDatabase(id)
        eventRepository.deleteById(eventFromDb.id)
    }

    private fun getEventFromDatabase(id: UUID) =
        eventRepository.findByIdOrNull(id)
            ?: throw EventNotFoundByIdException(id)

}