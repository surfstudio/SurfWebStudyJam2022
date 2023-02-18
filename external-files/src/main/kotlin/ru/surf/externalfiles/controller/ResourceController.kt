package ru.surf.externalfiles.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.dto.PostResponseDto
import ru.surf.externalfiles.service.ResourceFileService

@RestController
@RequestMapping("/resource")
class ResourceController(private val resourceFileService: ResourceFileService) {

   /* @GetMapping("/candidates", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getCandidates(): List<Candidate> = resourceFileService.parseHrExcelFile()*/

    @PostMapping(
        "/resume",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun saveResume(@RequestBody resume: MultipartFile): PostResponseDto = resourceFileService.saveResume(resume)

}