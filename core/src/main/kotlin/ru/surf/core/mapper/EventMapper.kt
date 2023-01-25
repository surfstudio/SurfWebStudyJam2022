package ru.surf.core.mapper

import ru.surf.core.dto.FullResponseEventDto
import ru.surf.core.dto.PostRequestEventDto
import ru.surf.core.dto.ShortResponseEventDto
import ru.surf.core.entity.Event


interface EventMapper {

    fun convertFromPostRequestEventDtoToEventEntity(postRequestEventDto: PostRequestEventDto): Event

    fun convertFromEventEntityToFullResponseEventDto(event: Event): FullResponseEventDto

    fun convertFromEventEntityToShortResponseEventDto(event: Event): ShortResponseEventDto

}