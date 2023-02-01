package ru.surf.externalfiles.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.surf.externalfiles.entity.Candidate
import ru.surf.externalfiles.service.ResourceFileService

@RestController
@RequestMapping("/resource")
class ResourceController(private val resourceFileService: ResourceFileService) {

    @GetMapping("/candidates", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getCandidates(): List<Candidate> = resourceFileService.parseHrExcelFile()

}