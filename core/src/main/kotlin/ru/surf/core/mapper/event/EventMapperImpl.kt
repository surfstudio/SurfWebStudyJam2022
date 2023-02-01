package ru.surf.core.mapper.event

import org.springframework.stereotype.Component
import ru.surf.core.dto.FullResponseEventDto
import ru.surf.core.dto.PostRequestEventDto
import ru.surf.core.dto.ShortResponseEventDto
import ru.surf.core.entity.Event

@Component
class EventMapperImpl : EventMapper {

    override fun convertFromPostRequestEventDtoToEventEntity(postRequestEventDto: PostRequestEventDto): Event =
        Event(
            description = postRequestEventDto.description, candidatesAmount = postRequestEventDto.candidatesAmount,
            traineesAmount = postRequestEventDto.traineesAmount, offersAmount = postRequestEventDto.offersAmount,
        )

    override fun convertFromEventEntityToFullResponseEventDto(event: Event): FullResponseEventDto =
        FullResponseEventDto(description = event.description ?: "")

    override fun convertFromEventEntityToShortResponseEventDto(event: Event): ShortResponseEventDto =
        ShortResponseEventDto(id = event.id)

}