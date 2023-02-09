package ru.surf.externalfiles.dto

import java.util.UUID

data class PostResponseResumeDto(
    val fileId: UUID,
    val sizeInBytes: Long,
    val s3Filename: String
)
