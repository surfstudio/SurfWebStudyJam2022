package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@Table(name = "question_variants", uniqueConstraints = [
        UniqueConstraint(columnNames = [
                "test_variant_id",
                "question_id"
        ]),
        UniqueConstraint(columnNames = [
                "test_variant_id",
                "question_order"
        ])
])
@Entity
class QuestionVariant(

        @Id
        @Column(name = "id")
        override val id: UUID = UUID.randomUUID(),

        @Column(name = "question_order", nullable = false)
        val questionOrder: Int = 0,

        @Column(name = "answered_at", nullable = false)
        var answeredAt: ZonedDateTime = ZonedDateTime.now(),

        @OneToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "question_id")
        val question: Question = Question(),

        @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "question_variant_id")
        var answers: Set<AnswerVariant> = setOf(),

        ) : UUIDBasedEntity(id) {

        override fun toString(): String {
                return "QuestionVariant(" +
                        "id=$id, " +
                        "questionOrder=$questionOrder, " +
                        "answeredAt=$answeredAt, " +
                        "question=$question, " +
                        "answers=$answers)"
        }

}
