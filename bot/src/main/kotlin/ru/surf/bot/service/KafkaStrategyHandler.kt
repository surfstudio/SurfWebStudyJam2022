package ru.surf.bot.service

import org.springframework.stereotype.Component
import ru.surf.core.kafkaEvents.bot.BotEvent
import ru.surf.core.kafkaEvents.bot.CreateDefenceNotificationBot
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class KafkaStrategyHandler {

    fun chooseNotificationStrategy(botEvent: BotEvent): String =
        when (botEvent) {
            is CreateDefenceNotificationBot -> "Назначена защита ${botEvent.eventName} \n" +
                    "Дата проведения: ${
                transformDate(
                    botEvent.date
                )
            } \n" + "Ссылка: ${botEvent.zoomLink}"
        }

    private fun transformDate(date: LocalDateTime): String =
        date.format(DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss")).toString()

}

