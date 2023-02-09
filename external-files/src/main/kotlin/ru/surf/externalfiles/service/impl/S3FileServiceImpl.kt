package ru.surf.externalfiles.service.impl

import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.configuration.S3PropertiesConfiguration
import ru.surf.externalfiles.entity.S3File
import ru.surf.externalfiles.service.S3DatabaseService
import ru.surf.externalfiles.service.S3FileService
import ru.surf.externalfiles.util.getS3Path
import software.amazon.awssdk.core.exception.SdkClientException
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.sql.SQLException


@Service
class S3FileServiceImpl(
    s3PropertiesConfiguration: S3PropertiesConfiguration,
    private val s3DatabaseService: S3DatabaseService,
    private val s3Client: S3Client,
) : S3FileService {

    private val bucketName = s3PropertiesConfiguration.bucketName

    @Transactional(rollbackFor = [SdkClientException::class, SQLException::class])
    override fun putObjectIntoS3Storage(multipartFile: MultipartFile): S3File {
        val file = multipartFile.inputStream
        val folder = getS3Path(multipartFile)
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(folder)
            .contentType(MediaType.APPLICATION_OCTET_STREAM.toString())
            .build()
        val fileData = RequestBody.fromInputStream(file, file.available().toLong())
        try {
            s3Client.putObject(putObjectRequest, fileData)
        } catch (e: Exception) {
            //TODO: поменять исключение
            when (e) {
                is SdkClientException -> throw RuntimeException()
                is SQLException -> throw RuntimeException()
            }
        }
        return s3DatabaseService.saveS3FileData(putObjectRequest, multipartFile)
    }

    override fun getObject(objectName: String): ByteArray {
        val getObjectRequest = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(objectName)
            .build()
        return try {
            s3Client.getObjectAsBytes(getObjectRequest).asByteArray()
        } catch (e: Exception) {
            //TODO: поменять исключение
            when (e) {
                is SdkClientException -> {
                    e.printStackTrace()
                    throw RuntimeException()
                }
                is SQLException -> {
                    e.printStackTrace()
                    throw RuntimeException()
                }
                else -> {
                    e.printStackTrace()
                    throw RuntimeException()
                }
            }
        }
    }

    @Transactional(rollbackFor = [SdkClientException::class, SQLException::class])
    override fun deleteObject(multipartFile: MultipartFile) {
        val folder = getS3Path(multipartFile)
        val deleteObjectRequest = DeleteObjectRequest.builder()
            .bucket(bucketName)
            .key(folder)
            .build()
        try {
            s3Client.deleteObject(deleteObjectRequest)
        } catch (e: Exception) {
            //TODO: поменять исключение
            when (e) {
                is SdkClientException -> throw RuntimeException()
                is SQLException -> throw RuntimeException()
            }
        }
    }
}