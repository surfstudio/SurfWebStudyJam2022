package ru.surf.core.dto

import java.time.LocalDateTime

data class ErrorDto(
    val type: String,
    val message: String,
    val date: LocalDateTime
)