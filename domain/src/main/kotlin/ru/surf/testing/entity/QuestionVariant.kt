package ru.surf.testing.entity

import java.time.ZonedDateTime
import java.util.*
import jakarta.persistence.*
import jakarta.persistence.CascadeType
import jakarta.persistence.Table
import org.hibernate.annotations.*
import ru.surf.core.entity.base.UUIDBasedEntity

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

        @Column(name = "answered_at", nullable = true)
        var answeredAt: ZonedDateTime? = null,

        @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.EAGER, optional = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        @JoinColumn(name = "question_id", nullable = false)
        @Fetch(value = FetchMode.JOIN)
        val question: Question = Question(),

        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinColumn(name = "question_variant_id", nullable = false)
        @BatchSize(size = 10)
        val answerVariants: MutableSet<AnswerVariant> = mutableSetOf(),

        @Formula("(SELECT GREATEST(sum(a.weight), 0) " +
                  "FROM answer_variants v " +
                  "JOIN answers a ON v.answer_id=a.id " +
                  "WHERE v.question_variant_id=id)")
        @Basic(fetch=FetchType.LAZY)
        val totalAnswersWeight: Double = 0.0,

        ) : UUIDBasedEntity(id) {

        val answered: Boolean
                get() = Objects.nonNull(answeredAt)

        override fun toString(): String {
                return "QuestionVariant(" +
                        "id=$id, " +
                        "questionOrder=$questionOrder, " +
                        "answeredAt=$answeredAt, " +
                        "question=$question, " +
                        "answered=$answered)"
        }

}
