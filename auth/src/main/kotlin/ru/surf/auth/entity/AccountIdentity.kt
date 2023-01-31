package ru.surf.auth.entity

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity


@Entity
class AccountIdentity(
        @Column(unique = true, columnDefinition = "uuid")
        var accountId: UUID = UUID.randomUUID()
) : Identity()
