package ru.surf.core.service

interface KafkaService {

    fun sendCoreEvent(event: Any)

}