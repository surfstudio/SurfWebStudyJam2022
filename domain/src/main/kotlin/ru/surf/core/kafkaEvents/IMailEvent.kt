package ru.surf.core.kafkaEvents

interface IMailEvent{
    val emailTo: String

    fun convertToParam(): Map<*, *>
}
