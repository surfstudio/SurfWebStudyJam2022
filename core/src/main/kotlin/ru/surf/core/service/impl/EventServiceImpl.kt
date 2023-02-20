package ru.surf.core.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.surf.core.dto.PostRequestEventDto
import ru.surf.core.dto.PutRequestEventDto
import ru.surf.core.dto.ShortResponseEventDto
import ru.surf.core.entity.Event
import ru.surf.core.exception.event.EventNotFoundByIdException
import ru.surf.core.mapper.event.EventMapper
import ru.surf.core.repository.EventRepository
import ru.surf.core.service.EventService
import ru.surf.core.service.SurfEmployeeService
import java.util.*


@Service
class EventServiceImpl(
        private val eventMapper: EventMapper,
        private val eventRepository: EventRepository,
        private val surfEmployeeService: SurfEmployeeService,
) : EventService {

    override fun createEvent(postRequestEventDto: PostRequestEventDto): ShortResponseEventDto {
        // TODO заглушка, убрать nullable когда будут готовы сервисы для связанных сущностей
        val transientEntity = eventMapper.convertFromPostRequestEventDtoToEventEntity(
                postRequestEventDto,
                // todo временно, посмотреть mapstruct
                eventInitiator = postRequestEventDto.eventInitiatorId?.let { surfEmployeeService.getSurfEmployee(it) }
        ).apply {
            eventTags = postRequestEventDto.eventTags
        }
        val persistedEvent = eventRepository.save(transientEntity)
        return eventMapper.convertFromEventEntityToShortResponseEventDto(persistedEvent)
    }

    override fun getEvent(id: UUID): Event {
        return getEventFromDatabase(id)
    }

    override fun updateEvent(id: UUID, putRequestEventDto: PutRequestEventDto): ShortResponseEventDto {
        val eventFromDb = getEventFromDatabase(id)
        eventFromDb.apply {
            title = putRequestEventDto.title
            description = putRequestEventDto.description
            candidatesAmount = putRequestEventDto.candidatesAmount
            offersAmount = putRequestEventDto.offersAmount
            traineesAmount = putRequestEventDto.traineesAmount
            eventTags = putRequestEventDto.eventTags
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