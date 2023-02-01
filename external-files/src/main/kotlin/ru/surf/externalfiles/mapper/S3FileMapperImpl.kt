package ru.surf.externalfiles.mapper

import org.apache.commons.codec.digest.DigestUtils
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.entity.S3File
import software.amazon.awssdk.services.s3.model.PutObjectRequest

@Component
class S3FileMapperImpl : S3FileMapper {

    companion object {
        const val unknown: String = "Unknown file"
    }

    override fun convertFromS3PutRequestToS3FileEntity(
        putObjectRequest: PutObjectRequest,
        multipartFile: MultipartFile
    ): S3File =
        S3File(
            contentType = putObjectRequest.contentType(),
            s3Key = putObjectRequest.key(),
            sizeInBytes = multipartFile.bytes.size.toLong(),
            s3Filename = multipartFile.originalFilename ?: unknown,
            checksum = DigestUtils.sha256Hex(multipartFile.inputStream)
        )
}