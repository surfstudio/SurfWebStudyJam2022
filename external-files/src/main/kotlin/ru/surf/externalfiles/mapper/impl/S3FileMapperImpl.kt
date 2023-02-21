package ru.surf.externalfiles.mapper.impl

import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.dto.PostResponseDto
import ru.surf.externalfiles.entity.S3File
import ru.surf.externalfiles.mapper.S3FileMapper
import ru.surf.externalfiles.mapper.impl.S3FileMapperImpl.FILENAME.unknown
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.time.ZonedDateTime

@Component
class S3FileMapperImpl(
        @Value("\${external-files.claim-interval-seconds}")
        private val claimIntervalSeconds: Long
) : S3FileMapper {

    object FILENAME {
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
            checksum = DigestUtils.sha256Hex(multipartFile.inputStream),
            expiresAt = ZonedDateTime.now().plusSeconds(claimIntervalSeconds)
        )

    override fun convertFromS3ResumeEntityToPostResponseResumeDto(s3ResumeFile: S3File): PostResponseDto =
        PostResponseDto(fileId = s3ResumeFile.id, size = s3ResumeFile.sizeInBytes, name = s3ResumeFile.s3Filename)

}