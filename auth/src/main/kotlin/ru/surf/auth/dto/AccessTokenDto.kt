package ru.surf.auth.dto

import java.io.Serializable


data class AccessTokenDto(
        val accessToken: String,
) : Serializable
