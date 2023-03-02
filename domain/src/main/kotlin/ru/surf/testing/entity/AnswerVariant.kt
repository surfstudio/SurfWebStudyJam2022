package ru.surf.testing.entity

import jakarta.persistence.*
import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*


@Table(name = "answer_variants", uniqueConstraints = [
        UniqueConstraint(columnNames = [
                "question_variant_id",
                "answer_id"
        ]),
])
@Entity
class AnswerVariant(

        @Id
        @Column(name = "id")
        override val id: UUID = UUID.randomUUID(),

        @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "answer_id", nullable = false)
        //@Fetch(value = FetchMode.JOIN)
        val answer: Answer = Answer(),

        ) : UUIDBasedEntity(id) {

        override fun toString(): String {
                return "AnswerVariant(" +
                        "id=$id, " +
                        "answer=$answer)"
        }

}