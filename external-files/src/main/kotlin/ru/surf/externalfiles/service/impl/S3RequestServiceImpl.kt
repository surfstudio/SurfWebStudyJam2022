package ru.surf.externalfiles.service.impl

import io.klogging.NoCoLogging
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.configuration.S3PropertiesConfiguration
import ru.surf.externalfiles.service.S3RequestService
import ru.surf.externalfiles.util.getS3Path
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest

@Service
class S3RequestServiceImpl(s3PropertiesConfiguration: S3PropertiesConfiguration) : S3RequestService {

    private val bucketName = s3PropertiesConfiguration.bucketName

    companion object : NoCoLogging

    override fun createS3PutRequest(multipartFile: MultipartFile): PutObjectRequest {
        return PutObjectRequest.builder()
            .bucket(bucketName)
            .key(getS3Path(multipartFile))
            .contentType(MediaType.APPLICATION_OCTET_STREAM.toString())
            .build()
            .also {
                logger.debug("Successfully creating s3PutRequest with data $it")
            }
    }

    override fun createS3GetRequest(s3Key: String): GetObjectRequest {
        return GetObjectRequest.builder()
            .bucket(bucketName)
            .key(s3Key)
            .build()
            .also {
                logger.debug("Successfully creating s3GetRequest with data $it")
            }
    }

    override fun createS3DeleteRequest(s3Key: String): DeleteObjectRequest {
        return DeleteObjectRequest.builder()
            .bucket(bucketName)
            .key(s3Key)
            .build()
            .also {
                logger.debug("Successfully creating s3DeleteRequest with data $it")
            }
    }

}