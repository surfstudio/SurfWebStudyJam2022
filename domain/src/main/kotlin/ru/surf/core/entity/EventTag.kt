package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*
import javax.persistence.*

@Table(name = "event_tags")
@Entity
class EventTag(

        @Id
        @Column(name = "id")
        override val id: UUID = UUID.randomUUID(),

        @Column(name = "description", nullable = false, unique = true)
        var description: String = "",

        ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , description = $description )"
    }

}