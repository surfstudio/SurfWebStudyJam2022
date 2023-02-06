package ru.surf.core.service

import ru.surf.core.dto.GeneralNotificationDto

interface KafkaService {

    fun sendGeneralNotificationMessage(generalNotificationDto: GeneralNotificationDto)
    
}