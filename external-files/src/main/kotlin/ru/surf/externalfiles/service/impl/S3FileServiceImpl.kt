package ru.surf.externalfiles.service.impl

import io.klogging.NoCoLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.service.S3DatabaseService
import ru.surf.externalfiles.service.S3FileService
import software.amazon.awssdk.awscore.exception.AwsServiceException
import software.amazon.awssdk.core.exception.SdkException
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.S3Exception
import java.time.ZonedDateTime
import java.util.*
import java.util.concurrent.TimeUnit


@Service
class S3FileServiceImpl(
    private val s3DatabaseService: S3DatabaseService,
    private val s3Client: S3Client,
) : S3FileService {

    companion object : NoCoLogging

    override fun putObjectIntoS3Storage(multipartFile: MultipartFile, putObjectRequest: PutObjectRequest) {
        val file = multipartFile.inputStream
        val fileData = RequestBody.fromInputStream(file, file.available().toLong())
        try {
            s3Client.putObject(putObjectRequest, fileData)
        } catch (e: Exception) {
            val errorMessage = "An error occurred when saving a file $file to s3 storage"
            when (e) {
                is S3Exception -> {
                    logger.error(errorMessage, e.message)
                    throw RuntimeException(errorMessage)
                }
                is AwsServiceException -> {
                    logger.error(errorMessage, e.message)
                    throw RuntimeException(errorMessage)
                }
                is SdkException -> {
                    logger.error(errorMessage, e.message)
                    throw RuntimeException(errorMessage)
                }
            }
        }
    }

    override fun getObject(getObjectRequest: GetObjectRequest): ByteArray {
        return try {
            s3Client.getObjectAsBytes(getObjectRequest).asByteArray()
        } catch (e: Exception) {
            val errorMessage =
                "An error occurred when getting a file with s3key ${getObjectRequest.key()} from s3 storage"
            when (e) {
                is S3Exception -> {
                    logger.error(errorMessage, e.message)
                    throw RuntimeException(errorMessage)
                }
                is AwsServiceException -> {
                    logger.error(errorMessage, e.message)
                    throw RuntimeException(errorMessage)
                }
                is SdkException -> {
                    logger.error(errorMessage, e.message)
                    throw RuntimeException(errorMessage)
                }
                else -> {
                    logger.error(errorMessage, e.message)
                    throw RuntimeException(errorMessage)
                }
            }
        }
    }

    override fun deleteObject(deleteObjectRequest: DeleteObjectRequest) {
        try {
            s3Client.deleteObject(deleteObjectRequest)
        } catch (e: Exception) {
            val errorMessage =
                "An error occurred when deleting a file with s3key ${deleteObjectRequest.key()} from s3 storage"
            when (e) {
                is S3Exception -> {
                    logger.error(errorMessage, e.message)
                    throw RuntimeException(errorMessage)
                }
                is AwsServiceException -> {
                    logger.error(errorMessage, e.message)
                    throw RuntimeException(errorMessage)
                }
                is SdkException -> {
                    logger.error(errorMessage, e.message)
                    throw RuntimeException(errorMessage)
                }
            }
        }
    }

    override fun claimFile(fileId: UUID): UUID =
        s3DatabaseService.keepS3FileData(fileId).id

    @Scheduled(fixedDelayString = "\${external-files.claim-interval-seconds}", timeUnit = TimeUnit.SECONDS)
    override fun cleanUnclaimedFiles() {
        s3DatabaseService.processExpiredFiles(ZonedDateTime.now(), s3DatabaseService::deleteS3FileData)
    }

}