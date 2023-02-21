package ru.surf.auth.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.surf.auth.entity.Credentials
import ru.surf.auth.entity.Identity
import java.util.UUID


@Repository
interface CredentialsRepository : JpaRepository<Credentials, UUID> {
    fun findByIdentity(identity: Identity): Credentials
}
