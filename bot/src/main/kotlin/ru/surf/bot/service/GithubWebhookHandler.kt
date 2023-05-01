package ru.surf.bot.service

import org.springframework.stereotype.Component

@Component
class GithubWebhookHandler {

    fun chooseActionStrategy(action: String): String =
        when (action) {
            "open" -> "Открыт Pull Request."
            "closed" -> "Закрыт Pull Request."
            "reopened" -> "Заново открыт Pull Request."
            else -> throw UnsupportedOperationException("UNSUPPORTED ACTION TYPE $action")
        }

}