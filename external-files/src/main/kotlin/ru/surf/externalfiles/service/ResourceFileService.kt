package ru.surf.externalfiles.service

import org.springframework.web.multipart.MultipartFile
import ru.surf.core.entity.Candidate
import ru.surf.externalfiles.dto.CandidateExcelDto
import ru.surf.externalfiles.dto.PostResponseDto
import java.util.UUID

interface ResourceFileService {

    fun parseHrExcelFile(fileId: UUID): List<CandidateExcelDto>

    fun saveResume(resume: MultipartFile): PostResponseDto

}