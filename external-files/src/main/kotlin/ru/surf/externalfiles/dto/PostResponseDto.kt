package ru.surf.externalfiles.dto

import java.util.UUID

data class PostResponseDto(
        val fileId: UUID,
        val name: String,
        val size: Long,
)
