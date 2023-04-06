package ru.surf.externalfiles.service.impl

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.service.S3DatabaseService
import ru.surf.externalfiles.service.S3FileService
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.util.*


@Service
class S3FileServiceImpl(
    private val s3DatabaseService: S3DatabaseService,
    private val s3Client: S3Client,
) : S3FileService {

    companion object {
        val logger = LoggerFactory.getLogger(S3FileServiceImpl::class.java)
    }

    override fun putObjectIntoS3Storage(multipartFile: MultipartFile, putObjectRequest: PutObjectRequest) {
        val file = multipartFile.inputStream
        val fileData = RequestBody.fromInputStream(file, file.available().toLong())
        try {
            s3Client.putObject(putObjectRequest, fileData)
        } catch (e: Exception) {
            val errorMessage = "An error occurred when saving a file $file to s3 storage"
            logger.info(errorMessage)
            throw RuntimeException(errorMessage)
        }
    }

    override fun getObject(getObjectRequest: GetObjectRequest): ByteArray {
        return try {
            s3Client.getObjectAsBytes(getObjectRequest).asByteArray()
        } catch (e: Exception) {
            val errorMessage =
                "An error occurred when getting a file with s3key ${getObjectRequest.key()} from s3 storage"
            logger.error(errorMessage)
            throw RuntimeException(errorMessage)
        }
    }

    override fun deleteObject(deleteObjectRequest: DeleteObjectRequest) {
        try {
            s3Client.deleteObject(deleteObjectRequest)
        } catch (e: Exception) {
            val errorMessage =
                "An error occurred when deleting a file with s3key ${deleteObjectRequest.key()} from s3 storage"
            logger.error(errorMessage)
            throw RuntimeException(errorMessage)
        }
    }

    override fun claimFile(fileId: UUID): UUID =
        s3DatabaseService.keepS3FileData(fileId).id

}