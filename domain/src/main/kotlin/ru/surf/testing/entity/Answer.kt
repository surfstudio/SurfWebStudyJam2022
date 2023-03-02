package ru.surf.testing.entity

import java.util.*
import jakarta.persistence.*
import ru.surf.core.entity.base.UUIDBasedEntity


@Table(name = "answers")
@Entity
class Answer(

        @Id
        @Column(name = "id")
        override val id: UUID = UUID.randomUUID(),

        @Column(name = "title", nullable = false)
        val title: String = "",

        @Column(name = "weight", nullable = false)
        val weight: Int = 0,

        ) : UUIDBasedEntity(id) {

    override fun toString(): String {
        return "Answer(" +
                "id=$id, " +
                "title='$title', " +
                "weight=$weight)"
    }

}