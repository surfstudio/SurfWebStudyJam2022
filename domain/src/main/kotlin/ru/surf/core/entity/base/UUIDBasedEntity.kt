package ru.surf.core.entity.base

import java.util.*
import jakarta.persistence.*

@MappedSuperclass
abstract class UUIDBasedEntity(

        @Id
        @Column(columnDefinition = "uuid")
        override val id: UUID
) : BaseEntity<UUID>()