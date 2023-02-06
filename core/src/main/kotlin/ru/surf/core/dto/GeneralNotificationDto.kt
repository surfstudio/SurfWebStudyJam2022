package ru.surf.core.dto

data class GeneralNotificationDto(
    val to: String,
    val subject: String,
    val notificationParams: Map<String, *>
)
