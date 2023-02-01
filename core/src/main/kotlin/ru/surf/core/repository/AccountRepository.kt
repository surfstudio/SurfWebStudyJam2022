package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.surf.core.entity.Account
import java.util.*

interface AccountRepository : JpaRepository<Account, UUID> {
    fun findByEmail(email: String): Optional<Account>
}