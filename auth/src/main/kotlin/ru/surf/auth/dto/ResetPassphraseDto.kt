package ru.surf.auth.dto


data class ResetPassphraseDto(
        val accessToken: String,
        val newPassphrase: String
)
