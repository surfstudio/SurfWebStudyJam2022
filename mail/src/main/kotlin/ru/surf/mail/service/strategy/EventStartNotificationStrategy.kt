package ru.surf.mail.service.strategy

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import org.thymeleaf.spring6.SpringTemplateEngine
import ru.surf.core.kafkaEvents.IMailEvent
import ru.surf.core.kafkaEvents.EmailType
import ru.surf.mail.model.Template
import ru.surf.core.kafkaEvents.CandidateEventNotification
import ru.surf.core.kafkaEvents.GeneralNotificationDto

@Component
class EventStartNotificationStrategy(
    private val javaMailSender: JavaMailSender,
    private val springTemplateEngine: SpringTemplateEngine,
) : EmailSendStrategy {
    override fun emailType(): EmailType = EmailType.EVENT_START_NOTIFICATION

    override fun sendEmail(email: IMailEvent) {
        if (email is CandidateEventNotification) {
            email.eventsName.forEach {
                val message = createMimeMessage(
                    GeneralNotificationDto(
                        emailType = EmailType.DEFAULT,
                        emailTo = email.emailTo,
                        subject = email.subject,
                        notificationParams = mapOf("eventName" to it)
                    ),
                    Template.EVENT_START_NOTIFICATION.html,
                    javaMailSender,
                    springTemplateEngine
                )
                javaMailSender.send(message)
            }
        }
    }
}
