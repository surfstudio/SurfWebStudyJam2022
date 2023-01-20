package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.surf.core.entity.Account
import java.util.UUID

interface AccountRepository : JpaRepository<Account, UUID> {
}