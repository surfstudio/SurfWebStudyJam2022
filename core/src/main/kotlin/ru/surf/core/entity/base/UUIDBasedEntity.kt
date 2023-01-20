package ru.surf.core.entity.base

import java.util.*
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class UUIDBasedEntity(

    @Id
    override val id: UUID
) : BaseEntity<UUID>()