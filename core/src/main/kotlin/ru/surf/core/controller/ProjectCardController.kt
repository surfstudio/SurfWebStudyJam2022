package ru.surf.core.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.surf.core.dto.card.ProjectCardResponseDto
import ru.surf.core.dto.card.PutRequestCardDto
import ru.surf.core.service.ProjectCardService
import java.util.*

@RestController
@RequestMapping("/cards")
class ProjectCardController(private val cardService: ProjectCardService) {

    @GetMapping("/{id}/card")
    fun getCard(@PathVariable(name = "id") eventId: UUID): ResponseEntity<ProjectCardResponseDto> =
        ResponseEntity.ok(cardService.getProjectCard(eventId))

    @PutMapping("/{id}")
    fun updateCard(
        @PathVariable(name = "id") eventId: UUID, @RequestBody putRequestCardDto: PutRequestCardDto
    ): ResponseEntity<UUID> =
        ResponseEntity.ok(cardService.updateProjectCard(eventId, putRequestCardDto))
}