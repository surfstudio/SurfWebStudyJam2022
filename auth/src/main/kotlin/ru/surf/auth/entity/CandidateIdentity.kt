package ru.surf.auth.entity

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity


@Entity
class CandidateIdentity(
        @Column(unique = true, columnDefinition = "uuid")
        var candidateId: UUID = UUID.randomUUID(),

        @Column(unique = true, columnDefinition = "uuid")
        var promotionId: UUID = UUID.randomUUID()
): Identity()
