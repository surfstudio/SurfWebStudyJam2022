package ru.surf.core.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.surf.core.dto.card.ProjectCardResponseDto
import ru.surf.core.dto.card.PutRequestCardDto
import ru.surf.core.mapper.card.CardMapper
import ru.surf.core.repository.ProjectCardRepository
import ru.surf.core.service.ProjectCardService
import java.util.*

@Service
class ProjectCardServiceImpl(
    private val projectCardRepository: ProjectCardRepository,
    private val cardMapper: CardMapper,
) : ProjectCardService {

    override fun getProjectCard(id: UUID): ProjectCardResponseDto = getProjectCardFromDb(id).run {
        cardMapper.convertFromProjectCardEntityToCardResponseDto(this).apply { eventName = "Surf Study Frontend" }
    }

    @Transactional
    override fun updateProjectCard(id: UUID, putRequestCardDto: PutRequestCardDto): UUID =
        getProjectCardFromDb(id).apply {
            this.projectNote = putRequestCardDto.projectNote
        }.run {
            this.id
        }

    private fun getProjectCardFromDb(id: UUID) =
        projectCardRepository.findByIdOrNull(id) ?: throw RuntimeException("NO CARD WITH ID $id")

}