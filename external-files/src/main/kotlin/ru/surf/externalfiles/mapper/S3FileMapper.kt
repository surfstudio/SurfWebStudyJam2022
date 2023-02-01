package ru.surf.externalfiles.mapper

import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.entity.S3File
import software.amazon.awssdk.services.s3.model.PutObjectRequest

interface S3FileMapper {

    fun convertFromS3PutRequestToS3FileEntity(putObjectRequest: PutObjectRequest, multipartFile: MultipartFile): S3File

}