package ru.surf.externalfiles.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.surf.externalfiles.entity.S3File
import java.time.ZonedDateTime
import java.util.UUID

@Repository
interface S3FileRepository : JpaRepository<S3File, UUID> {

    // todo проверить
    @Suppress("unused")
    fun getS3FileByChecksum(checksum: String): S3File?

    fun getS3FileByS3Key(s3key: String): S3File?

    fun findByExpiresAtLessThan(expiresAt: ZonedDateTime): MutableList<S3File>

}