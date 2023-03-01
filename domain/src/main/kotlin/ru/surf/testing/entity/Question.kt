package ru.surf.testing.entity

import java.util.*
import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.Formula
import ru.surf.core.entity.base.UUIDBasedEntity

@Table(name = "questions")
@Entity
class Question(

        @Id
        @Column(name = "id")
        override val id: UUID = UUID.randomUUID(),

        @Enumerated(EnumType.STRING)
        @Column(name = "question_type", nullable = false)
        val questionType: QuestionType = QuestionType.SINGLE_CHOICE,

        @Column(name = "title", nullable = false)
        val title: String = "",

        @Column(name = "weight", nullable = false)
        val weight: Int = 1,

        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinColumn(name = "question_id", nullable = false)
        @BatchSize(size = 10)
        val answers: Set<Answer> = mutableSetOf(),

        @Formula("(SELECT COALESCE(SUM(a.weight), 0) " +
                  "FROM answers a " +
                  "WHERE a.question_id=id AND a.weight > 0)")
        @Basic(fetch=FetchType.LAZY)
        val totalPositiveAnswersWeight: Double = 0.0,

        ) : UUIDBasedEntity(id) {

    override fun toString(): String {
        return "Question(" +
                "id=$id, " +
                "questionType=$questionType, " +
                "title='$title', " +
                "weight=$weight)"
    }

}
