package ru.surf.mail.service

import org.springframework.stereotype.Service
import ru.surf.core.kafkaEvents.IMailEvent
import ru.surf.mail.service.strategy.StrategyFactory

@Service
class EmailServiceImpl(
    val strategyFactory: StrategyFactory
) : EmailService {
    override fun sendEmail(email: IMailEvent) {
        val strategy = strategyFactory.findStrategy(email.emailType)
        strategy.sendEmail(email)
    }
}