package ru.surf.auth.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.surf.auth.entity.AccountIdentity
import ru.surf.auth.entity.CandidateIdentity
import ru.surf.auth.entity.Identity
import java.util.*


interface IdentityRepository : JpaRepository<Identity, Long> {
    fun findByCandidateId(candidateId: UUID): CandidateIdentity
    fun findByPromotionId(promotionId: UUID): CandidateIdentity
    fun findByAccountId(promotionId: UUID): AccountIdentity
}
