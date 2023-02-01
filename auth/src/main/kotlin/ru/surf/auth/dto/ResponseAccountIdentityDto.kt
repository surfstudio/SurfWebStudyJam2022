package ru.surf.auth.dto

import java.io.Serializable
import java.util.*


data class ResponseAccountIdentityDto(
        val identity: UUID,
) : Serializable
