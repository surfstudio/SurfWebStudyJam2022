package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*
import javax.persistence.*

@Table(name = "question_types")
@Entity
class QuestionType(

    @Id
    @Column(name = "id")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "type")
    val type: String = "",

    @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "type")
    val questions: List<Question> = emptyList(),

    ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , type = $type )"
    }

}