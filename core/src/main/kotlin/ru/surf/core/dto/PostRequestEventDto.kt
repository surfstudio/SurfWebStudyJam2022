package ru.surf.core.dto

import ru.surf.core.entity.EventTag
import java.util.UUID

data class PostRequestEventDto(
        val title: String?,
        val description: String?,
        val candidatesAmount: Int?,
        val traineesAmount: Int?,
        val offersAmount: Int?,
        val eventTags: Set<EventTag>,
        val eventInitiatorId: UUID?,
)