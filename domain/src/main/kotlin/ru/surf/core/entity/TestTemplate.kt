package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*
import javax.persistence.*

@Table(name = "test_templates")
@Entity
class TestTemplate(

        @Id
        @Column(name = "id")
        override val id: UUID = UUID.randomUUID(),

        @Column(name = "max_acceptable_duration_sec", nullable = false)
        val maxAcceptableDurationSec: Int = 0,

        @Column(name = "event_id", columnDefinition = "uuid", nullable = false, unique = true)
        val eventId: UUID = UUID.randomUUID(),

        @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "test_id")
        val questions: MutableSet<Question> = mutableSetOf()

) : UUIDBasedEntity(id) {

        override fun toString(): String {
                return "TestTemplate(" +
                        "id=$id, " +
                        "maxAcceptableDurationSec=$maxAcceptableDurationSec, " +
                        "eventId=$eventId, " +
                        "questions=$questions)"
        }

}