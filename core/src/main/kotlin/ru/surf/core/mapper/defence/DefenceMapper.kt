package ru.surf.core.mapper.defence

import ru.surf.core.dto.defence.PostRequestDefenceDto
import ru.surf.core.dto.defence.PostResponseDefenceDto
import ru.surf.core.entity.Defence

interface DefenceMapper {

    fun convertFromPostRequestDefenceDtoToDefenceEntity(postRequestDefenceDto: PostRequestDefenceDto): Defence

    fun convertFromDefenceEntityToPostResponseEntityDto(defence: Defence): PostResponseDefenceDto

}