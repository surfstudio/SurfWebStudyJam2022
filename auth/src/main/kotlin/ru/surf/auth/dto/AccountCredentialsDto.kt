package ru.surf.auth.dto

import java.io.Serializable
import java.util.*


data class AccountCredentialsDto (
        val identity: UUID,
        val passphrase: String
) : Serializable
