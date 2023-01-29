package ru.surf.auth.service


interface AuthService {
    fun login(identity: String, passphrase: String): String
    fun register(identity: String, passphrase: String)
    fun resetPassword(token: String, newPassphrase: String)
    fun validateToken(token: String): String
}
