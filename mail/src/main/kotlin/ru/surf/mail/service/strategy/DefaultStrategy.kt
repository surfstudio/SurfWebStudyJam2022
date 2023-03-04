package ru.surf.mail.service.strategy

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import org.thymeleaf.spring6.SpringTemplateEngine
import ru.surf.core.kafkaEvents.*
import ru.surf.mail.model.Template

@Component
class DefaultStrategy(
        private val javaMailSender: JavaMailSender,
        private val springTemplateEngine: SpringTemplateEngine,
): EmailSendStrategy {

    override fun emailType(): EmailType = EmailType.DEFAULT

    override fun sendEmail(email: IMailEvent) = getTemplateByEventType(email)?.run {
        createMimeMessage(email, html, javaMailSender, springTemplateEngine).let {
            javaMailSender.send(it)
        }
    } ?: throw RuntimeException("No template found for mail type ${email::class.qualifiedName}")

    private fun getTemplateByEventType(email: IMailEvent): Template? =
            when(email) {
                is CandidateEventNotification -> Template.EVENT_START_NOTIFICATION
                is CandidateAppliedEvent -> Template.ACCEPT_APPLICATION
                is TestCreatedEvent -> Template.TEST
                is TestPassedEvent -> Template.TEST_PASSED
                is GeneralNotificationDto -> null
            }

}