package ru.surf.testing.entity

import jakarta.persistence.*
import ru.surf.core.entity.base.UUIDBasedEntity
import java.time.ZonedDateTime
import java.util.*

@Table(name = "events_info")
@Entity
class EventInfo(

        @Id
        @Column(name="id")
        override val id: UUID = UUID.randomUUID(),

        @Column(name = "expected_testing_phase_deadline", nullable = false)
        val expectedTestingPhaseDeadline: ZonedDateTime = ZonedDateTime.now(),

        @Enumerated(EnumType.STRING)
        @Column(name = "testing_phase_state", nullable = false)
        var testingPhaseState: TestingPhaseState = TestingPhaseState.PENDING,


) : UUIDBasedEntity(id) {

    override fun toString(): String {
        return "EventInfo(" +
                "id=$id, " +
                "expectedTestingPhaseDeadline=$expectedTestingPhaseDeadline, " +
                "testingPhaseState=$testingPhaseState)"
    }
}