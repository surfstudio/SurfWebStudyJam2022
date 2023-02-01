package ru.surf.externalfiles.service

import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.services.s3.model.PutObjectRequest

interface S3DatabaseService {

    fun saveS3FileData(putObjectRequest: PutObjectRequest, multipartFile: MultipartFile)

    fun deleteS3FileData(filename: String)

}