package ru.surf.auth.dto

import java.io.Serializable


data class ResetPassphraseDto(
        val accessToken: String,
        val newPassphrase: String
) : Serializable
