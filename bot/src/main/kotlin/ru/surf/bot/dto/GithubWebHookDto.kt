package ru.surf.bot.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class GithubWebHookDto(
    @JsonProperty("action") val action: String,
    @JsonProperty("pull_request")
    val pullRequestDto: PullRequestDto
)
