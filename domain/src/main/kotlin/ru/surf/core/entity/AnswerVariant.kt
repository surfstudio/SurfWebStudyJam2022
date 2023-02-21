package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*
import javax.persistence.*


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

        @OneToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "answer_id")
        val answer: Answer = Answer(),

        ) : UUIDBasedEntity(id) {

        override fun toString(): String {
                return "AnswerVariant(id=$id, answer=$answer)"
        }

}