package ru.surf.core.mapper.event

import ru.surf.core.dto.FullResponseEventDto
import ru.surf.core.dto.PostRequestEventDto
import ru.surf.core.dto.ShortResponseEventDto
import ru.surf.core.entity.Event
import ru.surf.core.entity.SurfEmployee


interface EventMapper {

    // todo временно, посмотреть mapstruct
    fun convertFromPostRequestEventDtoToEventEntity(postRequestEventDto: PostRequestEventDto, eventInitiator: SurfEmployee?): Event

    fun convertFromEventEntityToFullResponseEventDto(event: Event): FullResponseEventDto

    fun convertFromEventEntityToShortResponseEventDto(event: Event): ShortResponseEventDto

}