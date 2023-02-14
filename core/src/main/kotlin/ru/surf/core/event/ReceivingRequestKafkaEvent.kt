package ru.surf.core.event

data class ReceivingRequestKafkaEvent(
    val emailTo: String,
    val eventName: String,
    val firstName: String,
    val lastName: String
)
