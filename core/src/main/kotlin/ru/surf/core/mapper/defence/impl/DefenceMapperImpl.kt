package ru.surf.core.mapper.defence.impl

import org.springframework.stereotype.Component
import ru.surf.core.dto.defence.PostRequestDefenceDto
import ru.surf.core.dto.defence.PostResponseDefenceDto
import ru.surf.core.mapper.defence.DefenceMapper
import ru.surf.core.entity.Defence
import java.time.LocalDateTime

@Component
class DefenceMapperImpl : DefenceMapper {

    // TODO: 16.03.2023 Настроить связи между сущностями, когда будет готова модель
    override fun convertFromPostRequestDefenceDtoToDefenceEntity(postRequestDefenceDto: PostRequestDefenceDto): Defence =
        Defence(
            title = postRequestDefenceDto.title,
            description = postRequestDefenceDto.description,
            createdAt = LocalDateTime.now(),
            date = postRequestDefenceDto.date,
            zoomLink = "NO LINK"
        )

    // TODO: 16.03.2023 Настроить связи между сущностями, когда будет готова модель
    override fun convertFromDefenceEntityToPostResponseEntityDto(defence: Defence): PostResponseDefenceDto =
        PostResponseDefenceDto(
            id = defence.id,
            zoomMeetingId = defence.zoomMeetingId,
            title = defence.title,
            description = defence.description,
            date = defence.date,
            zoomLink = defence.zoomLink,
            candidatesParticipants = listOf(),
            employeeParticipants = listOf()
        )

}