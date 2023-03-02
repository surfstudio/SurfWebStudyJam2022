package ru.surf.auth.entity

import java.util.*
import jakarta.persistence.Column
import jakarta.persistence.Entity


@Entity
class CandidateIdentity(
        @Column(unique = true, columnDefinition = "uuid")
        var candidateId: UUID = UUID.randomUUID(),

        @Column(unique = true, columnDefinition = "uuid")
        var promotionId: UUID = UUID.randomUUID()
): Identity()
