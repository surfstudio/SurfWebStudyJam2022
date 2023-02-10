package ru.surf.externalfiles.service

import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.entity.S3File
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.time.ZonedDateTime
import java.util.UUID

interface S3DatabaseService {

    fun saveS3FileData(putObjectRequest: PutObjectRequest, multipartFile: MultipartFile): S3File

    fun deleteS3FileData(fileId: UUID)

    fun getS3FileData(fileId: UUID): S3File

    fun persistS3File(id: UUID): S3File?

    fun processExpiredFiles(zonedDateTime: ZonedDateTime, applyFn: (UUID) -> Unit)

}