package ru.surf.externalfiles.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.surf.externalfiles.entity.S3File
import java.util.UUID

interface S3FileRepository : JpaRepository<S3File, UUID> {

    fun getS3FileByS3Filename(filename: String): S3File?

}