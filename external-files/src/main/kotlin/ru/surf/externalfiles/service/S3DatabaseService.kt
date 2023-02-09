package ru.surf.externalfiles.service

import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.entity.S3File
import software.amazon.awssdk.services.s3.model.PutObjectRequest

interface S3DatabaseService {

    fun saveS3FileData(putObjectRequest: PutObjectRequest, multipartFile: MultipartFile): S3File

    fun deleteS3FileData(filename: String)

}