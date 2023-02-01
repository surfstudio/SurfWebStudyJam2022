package ru.surf.externalfiles.service

import org.springframework.web.multipart.MultipartFile

interface S3FileService {

    fun putObjectIntoS3Storage(multipartFile: MultipartFile)

    fun getObject(objectName: String): ByteArray

    fun deleteObject(multipartFile: MultipartFile)

}

