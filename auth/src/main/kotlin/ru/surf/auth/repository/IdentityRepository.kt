package ru.surf.auth.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.surf.auth.entity.AccountIdentity
import ru.surf.auth.entity.ActivationIdentity
import ru.surf.auth.entity.Identity
import java.util.*


@Repository
interface IdentityRepository : JpaRepository<Identity, Long> {
    fun findBySubjectId(subjectId: UUID): ActivationIdentity
    fun findByActivationId(activationId: UUID): ActivationIdentity
    fun findByAccountId(activationId: UUID): AccountIdentity
}
