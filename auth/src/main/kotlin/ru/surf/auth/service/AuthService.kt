package ru.surf.auth.service


interface AuthService {
    fun register(username: String, password: String)
    fun resetPassword(token: String, password: String)
    fun login(username: String, password: String): String
    fun validateToken(token: String): String
}
