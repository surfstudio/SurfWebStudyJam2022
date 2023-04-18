package ru.surf.mail.service.strategy

import jakarta.mail.internet.MimeMessage
import org.springframework.core.io.ByteArrayResource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import ru.surf.core.kafkaEvents.EmailType
import ru.surf.core.kafkaEvents.MailEvent
import java.nio.charset.StandardCharsets

interface EmailSendStrategy {
    fun emailType(): EmailType
    fun sendEmail(email: MailEvent)

    fun createMimeMessage(
        email: MailEvent,
        templateLocation: String,
        javaMailSender: JavaMailSender,
        springTemplateEngine: SpringTemplateEngine
    ): MimeMessage {
        val message: MimeMessage = javaMailSender.createMimeMessage()
        val helper =
            MimeMessageHelper(
                message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
            )
        val context = Context()
        with(helper) {
            setTo(email.emailTo)
            setSubject(email.subject)
            email.getAttachment()
                .forEach {
                    addAttachment(email.emailType.toString().lowercase() + ".ics", ByteArrayResource(it))
                }
        }
        context.setVariables(email.params())
        val html: String = springTemplateEngine.process(templateLocation, context)
        helper.setText(html, true)
        return message
    }

}