package ru.surf.core.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import ru.surf.core.dto.FullResponseEventDto
import ru.surf.core.dto.PostRequestEventDto
import ru.surf.core.dto.PutRequestEventDto
import ru.surf.core.dto.ShortResponseEventDto
import ru.surf.core.entity.Candidate
import ru.surf.core.mapper.event.EventMapper
import ru.surf.core.service.CandidateService
import ru.surf.core.service.EventService
import ru.surf.externalfiles.dto.CandidateExcelDto
import java.util.*

@RestController
@RequestMapping("/events")
class EventController(
    private val eventService: EventService,
    private val eventMapper: EventMapper,
    private val candidateService: CandidateService,
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

    // TODO: 25.02.2023 Убрать в будущем
    @PostMapping("/notify/{id}")
    fun notifyCandidates(@PathVariable(name = "id") eventId: UUID, @RequestParam(name = "file_id") fileId: UUID) {
        val restTemplate: RestTemplate = RestTemplate()
        val url = "http://localhost:8081/external-files/resource/candidates"
        val support = UriComponentsBuilder.fromHttpUrl(url)
            .queryParam("file_id", fileId)
        val mapper = ObjectMapper()
        val forEntity: ResponseEntity<kotlin.collections.List<CandidateExcelDto>> =
            restTemplate.exchange(
                support.toUriString(),
                HttpMethod.GET,
                null,
                object : ParameterizedTypeReference<List<CandidateExcelDto>>() {}
            )
        candidateService.notifyCandidates(forEntity.body!!)
    }

    @GetMapping("/{id}/report")
    fun getReport(@PathVariable(name = "id") eventId: UUID): ResponseEntity<ByteArray> {
        val target = eventService.getReport(eventId)

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .body(target)
    }
}