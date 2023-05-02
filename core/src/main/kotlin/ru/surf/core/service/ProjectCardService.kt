package ru.surf.core.service

import ru.surf.core.dto.card.ProjectCardResponseDto
import ru.surf.core.dto.card.PutRequestCardDto
import java.util.UUID

interface ProjectCardService {

    fun createProjectCard(): Nothing = TODO()

    fun getProjectCard(id: UUID): ProjectCardResponseDto

    fun updateProjectCard(id: UUID, putRequestCardDto: PutRequestCardDto): UUID

}