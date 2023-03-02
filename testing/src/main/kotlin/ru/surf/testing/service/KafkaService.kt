package ru.surf.testing.service

interface KafkaService {

    fun sendCoreEvent(event: Any)

}