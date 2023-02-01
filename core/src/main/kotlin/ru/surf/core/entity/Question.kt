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

    @Column(name = "question")
    var description: String = "",

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "question_type_id")
    var type: QuestionType = QuestionType(),

    @ManyToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinTable(
        name = "questions_answers",
        joinColumns = [JoinColumn(name = "answer_id")],
        inverseJoinColumns = [JoinColumn(name = "question_id")]
    )
    var answers: MutableList<Answer> = mutableListOf(),

    @OneToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.EAGER)
    @JoinColumn(name = "right_answer_id", referencedColumnName = "id")
    var rightAnswer: Answer = Answer(),

    ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , description = $description , rightAnswer = $rightAnswer )"
    }

}
