package ru.surf.externalfiles.service

import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.util.UUID

interface S3FileService {

    fun putObjectIntoS3Storage(multipartFile: MultipartFile, putObjectRequest: PutObjectRequest)

    fun getObject(getObjectRequest: GetObjectRequest): ByteArray

    fun deleteObject(deleteObjectRequest: DeleteObjectRequest)

    fun claimFile(fileId: UUID): UUID

    fun cleanUnclaimedFiles()

}

