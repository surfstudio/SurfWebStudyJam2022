package ru.surf.testing.entity

import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*

@Table(name = "test_templates")
@Entity
class TestTemplate(

        @Id
        @Column(name = "id")
        override val id: UUID = UUID.randomUUID(),

        @Column(name = "max_acceptable_duration_sec", nullable = false)
        val maxAcceptableDurationSec: Long = 0,

        @Column(name = "max_questions_pool_size", nullable = false)
        val maxQuestionPoolSize: Int = Int.MAX_VALUE,

        @OneToOne(cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST], fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false, unique = true)
        val eventInfo: EventInfo = EventInfo(),

        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinColumn(name = "test_id", nullable = false)
        @BatchSize(size = 10)
        val questions: Set<Question> = mutableSetOf()

        ) : UUIDBasedEntity(id) {

        override fun toString(): String {
                return "TestTemplate(" +
                        "id=$id, " +
                        "maxAcceptableDurationSec=$maxAcceptableDurationSec, " +
                        "maxQuestionPoolSize=$maxQuestionPoolSize, " +
                        "eventId=${eventInfo.id})"
        }

}