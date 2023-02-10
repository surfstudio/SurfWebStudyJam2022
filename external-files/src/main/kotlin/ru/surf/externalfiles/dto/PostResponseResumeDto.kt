package ru.surf.externalfiles.dto

import java.util.UUID

data class PostResponseResumeDto(
        val fileId: UUID,
        val name: String,
        val size: Long,
)
