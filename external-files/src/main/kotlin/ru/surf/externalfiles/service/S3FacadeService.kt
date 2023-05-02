package ru.surf.externalfiles.service

import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.dto.PostResponseDto
import java.util.UUID

interface S3FacadeService {

    fun saveFile(file: MultipartFile): PostResponseDto

    fun getFile(fileId: UUID): ByteArray

    fun deleteFile(fileId: UUID)

    fun cleanUnclaimedFiles()

}