package ru.surf.core.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.surf.core.dto.event.PostRequestEventDto
import ru.surf.core.dto.event.PutRequestEventDto
import ru.surf.core.dto.event.ShortResponseEventDto
import ru.surf.core.entity.Event
import ru.surf.core.entity.EventState
import ru.surf.core.exception.event.EventNotFoundByIdException
import ru.surf.core.exception.event.EventReportNotFoundException
import ru.surf.core.mapper.event.EventMapper
import ru.surf.core.repository.EventRepository
import ru.surf.core.service.EventService
import ru.surf.core.service.SurfEmployeeService
import ru.surf.externalfiles.service.S3FacadeService
import java.time.ZonedDateTime
import java.util.*


@Service
class EventServiceImpl(
    private val eventMapper: EventMapper,
    private val eventRepository: EventRepository,
    private val surfEmployeeService: SurfEmployeeService,
    private val s3FacadeService: S3FacadeService
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
        transientEntity.eventStates = setOf(
                EventState(
                        stateDate = ZonedDateTime.now(),
                        stateType = EventState.StateType.APPLYING
                )
        )
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

    override fun getReport(id: UUID): ByteArray {
        val reportFileId = eventRepository.getReportFileId(id) ?: throw EventReportNotFoundException(id)

        return s3FacadeService.getFile(reportFileId)
    }

    override fun getCandidatesReport(id: UUID): ByteArray {
        val candidatesReportFileId = eventRepository.getCandidatesReportFileId(id) ?: throw EventReportNotFoundException(id)

        return s3FacadeService.getFile(candidatesReportFileId)
    }

    override fun deleteEvent(id: UUID) {
        val eventFromDb = getEventFromDatabase(id)
        eventRepository.deleteById(eventFromDb.id)
    }

    private fun getEventFromDatabase(id: UUID) =
        eventRepository.findByIdOrNull(id)
            ?: throw EventNotFoundByIdException(id)

}