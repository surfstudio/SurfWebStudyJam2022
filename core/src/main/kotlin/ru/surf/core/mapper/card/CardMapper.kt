package ru.surf.core.mapper.card

import ru.surf.core.dto.card.ProjectCardResponseDto
import ru.surf.core.entity.ProjectCard

interface CardMapper {

    fun convertFromProjectCardEntityToCardResponseDto(projectCard: ProjectCard): ProjectCardResponseDto

}