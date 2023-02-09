package ru.surf.externalfiles.service

import org.springframework.web.multipart.MultipartFile
import ru.surf.core.entity.Candidate
import ru.surf.externalfiles.dto.PostResponseResumeDto

interface ResourceFileService {

    fun parseHrExcelFile(): List<Candidate>

    fun saveResume(resume: MultipartFile): PostResponseResumeDto

}