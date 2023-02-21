package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*
import javax.persistence.*

@Table(name = "questions")
@Entity
class Question(

        @Id
        @Column(name = "id")
        override val id: UUID = UUID.randomUUID(),

        @Enumerated(EnumType.STRING)
        @Column(name = "question_type", nullable = false)
        val questionType: QuestionType = QuestionType.SINGE_CHOICE,

        @Column(name = "title", nullable = false)
        val title: String = "",

        @Column(name = "weight", nullable = false)
        var weight: Int = 0,

        @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "question_id")
        val answers: Set<Answer> = setOf(),

        ) : UUIDBasedEntity(id) {

    override fun toString(): String {
        return "Question(" +
                "id=$id, " +
                "questionType=$questionType, " +
                "title='$title', " +
                "weight=$weight, " +
                "answers=$answers)"
    }

    @Suppress("unused")
    enum class QuestionType {
        SINGE_CHOICE,
        MULTIPLE_CHOICE
    }

}
