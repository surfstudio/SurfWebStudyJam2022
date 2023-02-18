package ru.surf.externalfiles.service

import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest

interface S3RequestService {

    fun createS3PutRequest(multipartFile: MultipartFile): PutObjectRequest

    fun createS3GetRequest(s3Key: String): GetObjectRequest

    fun createS3DeleteRequest(s3Key: String): DeleteObjectRequest

}