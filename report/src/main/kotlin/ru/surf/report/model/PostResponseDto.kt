package ru.surf.report.model

import java.util.UUID


data class PostResponseDto(
    val fileId: UUID,
    val name: String,
    val size: Long,
)
