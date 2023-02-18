package ru.surf.externalfiles.service

import org.springframework.web.multipart.MultipartFile
import ru.surf.core.entity.Candidate
import ru.surf.externalfiles.dto.PostResponseDto

interface ResourceFileService {

    // TODO: 18.02.2023 Имплементировать логику потом 
   /* fun parseHrExcelFile(): List<Candidate>*/

    fun saveResume(resume: MultipartFile): PostResponseDto

}