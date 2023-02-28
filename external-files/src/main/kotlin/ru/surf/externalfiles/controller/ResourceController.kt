package ru.surf.externalfiles.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.dto.CandidateExcelDto
import ru.surf.externalfiles.dto.PostResponseDto
import ru.surf.externalfiles.service.ResourceFileService
import java.util.UUID

@RestController
@RequestMapping("/resource")
class ResourceController(private val resourceFileService: ResourceFileService) {

    @GetMapping("/candidates", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getCandidates(@RequestParam("file_id") fileId: UUID): List<CandidateExcelDto> =
        resourceFileService.parseHrExcelFile(fileId)

    @PostMapping(
        "/resume",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun saveResume(@RequestBody resume: MultipartFile): PostResponseDto = resourceFileService.saveResume(resume)

}