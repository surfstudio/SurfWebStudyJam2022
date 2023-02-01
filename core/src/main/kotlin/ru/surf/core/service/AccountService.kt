package ru.surf.core.service

import ru.surf.core.entity.Account
import java.util.*

interface AccountService {

    fun getAccountByEmail(email: String): Account?
    fun getAccountById(id: UUID): Account?

}