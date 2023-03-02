package ru.surf.testing.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.surf.testing.dto.NextTestingPhaseStateDto
import ru.surf.testing.service.EventInfoService
import java.util.UUID

@RestController
@RequestMapping(value = ["/event_info"])
class EventInfoController(

        @Autowired
        private val eventInfoService: EventInfoService

) {

    @PostMapping(value = ["/state/next/{eventId}"])
    fun moveToNextTestingPhaseState(@PathVariable eventId: UUID): ResponseEntity<NextTestingPhaseStateDto> =
            eventInfoService.moveToNextTestingPhaseState(eventId).let {
                ResponseEntity.ok(NextTestingPhaseStateDto(eventId=eventId, state=it))
            }

}