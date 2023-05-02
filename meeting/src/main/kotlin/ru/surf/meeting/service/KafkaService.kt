package ru.surf.meeting.service

interface KafkaService {

    fun sendCoreEvent(event: Any)

}