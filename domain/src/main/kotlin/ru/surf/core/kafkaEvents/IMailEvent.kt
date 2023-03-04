package ru.surf.core.kafkaEvents

sealed interface IMailEvent{
    val emailType: EmailType
    val emailTo: String
    val subject: String
    fun params(): Map<String, *>
}

enum class EmailType {
    ACCEPT_APPLICATION,
    EVENT_START_NOTIFICATION,
    DEFAULT
}