package ru.surf.core.kafkaEvents

import java.util.UUID

data class EndingEvent(
    val eventId: UUID
)
