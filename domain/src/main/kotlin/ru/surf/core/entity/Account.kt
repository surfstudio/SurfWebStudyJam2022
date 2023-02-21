package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email

@Table(name = "accounts")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
open class Account(

        @Id
        @Column(name = "id")
        override val id: UUID = UUID.randomUUID(),

        @Column(name = "email", nullable = false, unique = true)
        @Email(regexp = ".+?@.+")
        open val email: String = "",

        @Enumerated(EnumType.STRING)
        @Column(name = "role", nullable = false)
        open val role: Role = Role.TRAINEE,

        @Column(name = "created_at", nullable = false)
        val createdAt: ZonedDateTime = ZonedDateTime.now(),

        ) : UUIDBasedEntity(id) {

    override fun toString(): String {
        return "Account(" +
                "id=$id, " +
                "email='$email', " +
                "role=$role, " +
                "createdAt=$createdAt)"
    }

    @Suppress("unused")
    enum class Role {
        TRAINEE,
        SURF_EMPLOYEE
    }

}