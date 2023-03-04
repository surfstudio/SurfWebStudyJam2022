package ru.surf.mail.service

import ru.surf.core.kafkaEvents.IMailEvent

interface EmailService {
    fun sendEmail(email: IMailEvent)
}