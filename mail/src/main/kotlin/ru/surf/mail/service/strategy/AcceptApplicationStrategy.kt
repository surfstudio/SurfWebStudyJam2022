package ru.surf.mail.service.strategy

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import org.thymeleaf.spring6.SpringTemplateEngine
import ru.surf.core.kafkaEvents.IMailEvent
import ru.surf.core.kafkaEvents.EmailType
import ru.surf.mail.model.Template

@Component
class AcceptApplicationStrategy(
        private val javaMailSender: JavaMailSender,
        private val springTemplateEngine: SpringTemplateEngine,
) : EmailSendStrategy {

    override fun emailType(): EmailType = EmailType.ACCEPT_APPLICATION

    override fun sendEmail(email: IMailEvent) {
        val message = createMimeMessage(email, Template.ACCEPT_APPLICATION.html, javaMailSender, springTemplateEngine)
        javaMailSender.send(message)
    }
}