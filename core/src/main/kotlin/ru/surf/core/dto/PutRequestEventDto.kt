package ru.surf.core.dto

import java.util.*

data class PutRequestEventDto(
    val description: String?,
    val candidatesAmount: Int?,
    val traineesAmount: Int?,
    val offersAmount: Int?,
    val eventTypeId: UUID,
    val eventInitiatorId: UUID
) {
}