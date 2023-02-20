package ru.surf.core.mapper.event.impl

import org.springframework.stereotype.Component
import ru.surf.core.dto.FullResponseEventDto
import ru.surf.core.dto.PostRequestEventDto
import ru.surf.core.dto.ShortResponseEventDto
import ru.surf.core.entity.Event
import ru.surf.core.entity.SurfEmployee
import ru.surf.core.mapper.event.EventMapper

@Component
class EventMapperImpl : EventMapper {

    // todo временно, посмотреть mapstruct
    override fun convertFromPostRequestEventDtoToEventEntity(postRequestEventDto: PostRequestEventDto, eventInitiator: SurfEmployee?): Event =
        Event(
            title = postRequestEventDto.title ?: "",
            description = postRequestEventDto.description ?: "",
            candidatesAmount = postRequestEventDto.candidatesAmount ?: 0,
            traineesAmount = postRequestEventDto.traineesAmount ?: 0,
            offersAmount = postRequestEventDto.offersAmount ?: 0,
            eventInitiator = eventInitiator
        )

    override fun convertFromEventEntityToFullResponseEventDto(event: Event): FullResponseEventDto =
        FullResponseEventDto(
                id=event.id,
                title=event.title,
                states=event.eventStates
        )

    override fun convertFromEventEntityToShortResponseEventDto(event: Event): ShortResponseEventDto =
        ShortResponseEventDto(id = event.id)

}