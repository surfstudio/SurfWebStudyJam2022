package ru.surf.externalfiles.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.surf.externalfiles.entity.S3File
import java.time.ZonedDateTime
import java.util.UUID

interface S3FileRepository : JpaRepository<S3File, UUID> {

    fun getS3FileByChecksum(checksum: String): S3File?

    fun getS3FileByS3Key(s3key: String): S3File?

    fun findByExpiresAtLessThan(expiresAt: ZonedDateTime): MutableList<S3File>

}