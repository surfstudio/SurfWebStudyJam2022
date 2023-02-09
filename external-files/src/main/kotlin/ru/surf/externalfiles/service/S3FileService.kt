package ru.surf.externalfiles.service

import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.entity.S3File

interface S3FileService {

    fun putObjectIntoS3Storage(multipartFile: MultipartFile): S3File

    fun getObject(objectName: String): ByteArray

    fun deleteObject(multipartFile: MultipartFile)

}

