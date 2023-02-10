package ru.surf.externalfiles.service

import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.entity.S3File
import java.util.UUID

interface S3FileService {

    fun putObjectIntoS3Storage(multipartFile: MultipartFile): S3File

    fun getObject(objectName: String): ByteArray

    fun deleteObject(fileId: UUID)

    fun claimFile(fileId: UUID): UUID?

    fun cleanUnclaimedFiles()

}

