package ru.surf.core.kafkaEvents.bot

import java.time.LocalDateTime

data class CreateDefenceNotificationBot(
    val eventName: String,
    val date: LocalDateTime,
    val zoomLink: String
) : BotEvent {
    override fun params(): Map<String, *> = mapOf(
        "eventName" to eventName,
        "date" to date,
        "zoomLink" to zoomLink
    )
}