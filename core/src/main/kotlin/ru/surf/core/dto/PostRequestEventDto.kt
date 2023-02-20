package ru.surf.core.dto

import java.util.UUID

data class PostRequestEventDto(
        val title: String?,
        val description: String?,
        val candidatesAmount: Int?,
        val traineesAmount: Int?,
        val offersAmount: Int?,
        val eventTagsId: Collection<UUID>,
        val eventInitiatorId: UUID?,
)