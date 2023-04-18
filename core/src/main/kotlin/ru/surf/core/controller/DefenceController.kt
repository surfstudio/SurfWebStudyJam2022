package ru.surf.core.controller

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.surf.core.dto.defence.PostRequestDefenceDto
import ru.surf.core.dto.defence.PostResponseDefenceDto
import ru.surf.core.service.DefenceService
import java.util.UUID

@RestController
@RequestMapping("/defence")
class DefenceController(private val defenceService: DefenceService) {

    @PostMapping("")
    fun registerDefence(
        @RequestBody postRequestDefenceDto: PostRequestDefenceDto
    ): ResponseEntity<PostResponseDefenceDto> =
        ResponseEntity(defenceService.createDefence(postRequestDefenceDto), HttpStatus.CREATED)

    @DeleteMapping("/{id}")
    fun cancelDefence(@PathVariable id: UUID): ResponseEntity<Unit> =
        ResponseEntity(defenceService.deleteDefence(id), HttpStatus.OK)

}