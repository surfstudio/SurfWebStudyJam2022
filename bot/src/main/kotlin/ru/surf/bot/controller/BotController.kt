package ru.surf.bot.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.surf.bot.dto.GithubWebHookDto
import ru.surf.bot.service.BotService

@RequestMapping("/bot")
@RestController
class BotController(private val botService: BotService) {

    @PostMapping("/pr")
    fun webHookForCommit(@RequestBody hookData: GithubWebHookDto) = botService.sendWebhookNotification(hookData)

}