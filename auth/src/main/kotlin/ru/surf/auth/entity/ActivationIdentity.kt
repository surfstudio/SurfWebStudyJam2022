package ru.surf.auth.entity

import java.util.*
import jakarta.persistence.Column
import jakarta.persistence.Entity


@Entity
class ActivationIdentity(
        @Column(unique = true, columnDefinition = "uuid")
        var subjectId: UUID = UUID.randomUUID(),

        @Column(unique = true, columnDefinition = "uuid")
        var activationId: UUID = UUID.randomUUID()
): Identity()
