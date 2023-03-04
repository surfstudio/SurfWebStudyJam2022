package ru.surf.mail.service.strategy

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.surf.core.kafkaEvents.EmailType


@Component
class StrategyFactory(
        @Autowired
        private val defaultStrategy: DefaultStrategy
) {
    private var strategies : Map<EmailType, EmailSendStrategy> = mutableMapOf()

    fun findStrategy(strategyName: EmailType): EmailSendStrategy {
        return strategies.getOrDefault(strategyName, defaultStrategy)
    }

    @Autowired
    fun createStrategies(strategySet: Set<EmailSendStrategy>) {
        strategies = strategySet.associateBy { it.emailType() }
    }
}