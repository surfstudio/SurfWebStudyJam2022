package ru.surf.core.kafkaEvents

sealed interface MailEvent {
    val emailType: EmailType
    val emailTo: String
    val subject: String
    fun getAttachment(): List<ByteArray> = listOf()
    fun params(): Map<String, *>
}

enum class EmailType {
    ACCEPT_APPLICATION,
    EVENT_START_NOTIFICATION,
    DEFENCE_CREATE_NOTIFICATION,
    DEFENCE_CANCEL_NOTIFICATION,
    DEFAULT
}