package ru.surf.core.kafkaEvents

sealed interface IMailEvent{
    val emailTo: String

    fun convertToParam(): Map<*, *>
}
