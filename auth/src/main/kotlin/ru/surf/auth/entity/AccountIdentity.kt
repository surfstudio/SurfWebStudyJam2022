package ru.surf.auth.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.util.*


@Entity
class AccountIdentity(
        @Column(unique = true, columnDefinition = "uuid")
        var accountId: UUID = UUID.randomUUID()
) : Identity()
