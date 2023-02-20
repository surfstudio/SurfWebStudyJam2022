package ru.surf.core.dto

import ru.surf.core.entity.EventTag

data class PutRequestEventDto(
        val title: String,
        val description: String,
        val candidatesAmount: Int,
        val traineesAmount: Int,
        val offersAmount: Int,
        val eventTags: Set<EventTag>
)