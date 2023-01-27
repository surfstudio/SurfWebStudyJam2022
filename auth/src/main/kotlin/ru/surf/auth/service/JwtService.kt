package ru.surf.auth.service

import com.auth0.jwt.interfaces.DecodedJWT


interface JwtService {
    fun validateJwt(token: String): DecodedJWT
}
