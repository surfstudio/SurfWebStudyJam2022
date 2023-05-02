package ru.surf.mail.service

import ru.surf.core.kafkaEvents.MailEvent

interface EmailService {
    fun sendEmail(email: MailEvent)
}