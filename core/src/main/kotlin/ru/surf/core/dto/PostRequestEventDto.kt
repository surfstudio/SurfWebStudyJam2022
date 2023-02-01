package ru.surf.core.dto

import java.util.UUID

data class PostRequestEventDto(
    val initiatorId: UUID?,
    val description: String?,
    val candidatesAmount: Int?,
    val traineesAmount: Int?,
    val offersAmount: Int?,
    val eventTypeId: UUID?,
) {
}