package ru.surf.core.mapper.card.impl

import org.springframework.stereotype.Component
import ru.surf.core.dto.card.ProjectCardResponseDto
import ru.surf.core.entity.ProjectCard
import ru.surf.core.mapper.card.CardMapper

@Component
class CardMapperImpl : CardMapper {
    override fun convertFromProjectCardEntityToCardResponseDto(projectCard: ProjectCard): ProjectCardResponseDto =
        ProjectCardResponseDto(
            eventName = "",
            title = projectCard.title,
            githubLink = projectCard.projectInfo.gitLink,
            miroLink = projectCard.projectInfo.miroLink,
            trelloLink = projectCard.projectInfo.trelloLink,
            googleDriveStorageLink = projectCard.projectInfo.googleDriveLink,
            usefulDocumentationLink = projectCard.projectInfo.usefulResourcesLink,
            projectNote = projectCard.projectNote,
            version = projectCard.version
        )
}