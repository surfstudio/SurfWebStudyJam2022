package ru.surf.core.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.surf.core.dto.FullResponseEventDto
import ru.surf.core.dto.PostRequestEventDto
import ru.surf.core.dto.PutRequestEventDto
import ru.surf.core.dto.ShortResponseEventDto
import ru.surf.core.entity.Candidate
import ru.surf.core.mapper.event.EventMapper
import ru.surf.core.service.CandidateService
import ru.surf.core.service.EventService
import java.util.UUID

@RestController
@RequestMapping("/events")
class EventController(
    private val eventService: EventService,
    private val eventMapper: EventMapper,
    private val candidateService: CandidateService
) {

    @PostMapping("/event")
    fun createEvent(@RequestBody postRequestEventDto: PostRequestEventDto): ResponseEntity<ShortResponseEventDto> {
        val shortResponseEventDto = eventService.createEvent(postRequestEventDto)
        return ResponseEntity.ok(shortResponseEventDto)
    }

    @GetMapping("/{id}")
    fun getEvent(@PathVariable(name = "id") eventId: UUID): ResponseEntity<FullResponseEventDto> {
        val fullResponseEventDto = eventService.getEvent(eventId)
        return ResponseEntity.ok(eventMapper.convertFromEventEntityToFullResponseEventDto(fullResponseEventDto))
    }

    @PutMapping("/{id}")
    fun updateEvent(
        @PathVariable(name = "id") eventId: UUID,
        @RequestBody putRequestEventDto: PutRequestEventDto
    ): ResponseEntity<ShortResponseEventDto> {
        val shortResponseEventDto = eventService.updateEvent(eventId, putRequestEventDto)
        return ResponseEntity.ok(shortResponseEventDto)
    }

    @DeleteMapping("/{id}")
    fun deleteEvent(@PathVariable(name = "id") eventId: UUID): ResponseEntity<Unit> {
        eventService.deleteEvent(eventId)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{id}/preferred")
    fun getPreferredCandidates(@PathVariable(name = "id") eventId: UUID): ResponseEntity<Map<Candidate, List<String>>> =
        ResponseEntity.ok(candidateService.getPreferredCandidates(eventId))

}