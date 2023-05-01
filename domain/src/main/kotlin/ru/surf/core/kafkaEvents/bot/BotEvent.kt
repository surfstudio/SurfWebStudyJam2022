package ru.surf.core.kafkaEvents.bot

sealed interface BotEvent {
    fun params(): Map<String, *>
}