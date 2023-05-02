package ru.surf.core.mapper.event

import ru.surf.core.dto.event.FullResponseEventDto
import ru.surf.core.dto.event.PostRequestEventDto
import ru.surf.core.dto.event.ShortResponseEventDto
import ru.surf.core.entity.Event
import ru.surf.core.entity.SurfEmployee


interface EventMapper {

    // todo временно, посмотреть mapstruct
    fun convertFromPostRequestEventDtoToEventEntity(postRequestEventDto: PostRequestEventDto, eventInitiator: SurfEmployee?): Event

    fun convertFromEventEntityToFullResponseEventDto(event: Event): FullResponseEventDto

    fun convertFromEventEntityToShortResponseEventDto(event: Event): ShortResponseEventDto

}