package ru.surf.auth.dto

import java.util.*


data class AccountCredentialsDto(
        val identity: UUID,
        val passphrase: String
)
