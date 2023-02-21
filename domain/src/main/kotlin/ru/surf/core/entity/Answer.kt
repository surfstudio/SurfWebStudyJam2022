package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


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