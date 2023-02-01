package ru.surf.externalfiles.entity

import ru.surf.externalfiles.entity.base.UUIDBasedEntity
import java.util.*
import javax.persistence.*


@Table(name = "answers")
@Entity
class Answer(

    @Id
    @Column(name = "id")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "answer")
    var text: String = "",

    @ManyToMany(mappedBy = "answers", fetch = FetchType.LAZY)
    val questions: Set<Question> = mutableSetOf()

    ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , text = $text )"
    }
    
}