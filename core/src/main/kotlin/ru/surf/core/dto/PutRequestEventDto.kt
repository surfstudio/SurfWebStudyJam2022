package ru.surf.core.dto

import java.util.*

data class PutRequestEventDto(
        val title: String,
        val description: String,
        val candidatesAmount: Int,
        val traineesAmount: Int,
        val offersAmount: Int,
        val eventTagsId: Collection<UUID>,
        val eventInitiatorId: UUID
)