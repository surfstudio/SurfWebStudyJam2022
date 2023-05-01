package ru.surf.bot.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class PullRequestDto(@JsonProperty("html_url") val url: String)
