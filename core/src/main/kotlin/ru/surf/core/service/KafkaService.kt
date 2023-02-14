package ru.surf.core.service

import ru.surf.core.event.ReceivingRequestKafkaEvent

interface KafkaService {

    fun sendReceivingRequestEvent(receivingRequestKafkaEvent: ReceivingRequestKafkaEvent)

}