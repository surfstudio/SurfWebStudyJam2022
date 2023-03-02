package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.time.ZonedDateTime
import java.util.*
import jakarta.persistence.*

@Table(name = "event_states", uniqueConstraints = [
    UniqueConstraint(columnNames = [
        "state_type",
        "event_id"
    ])
])
@Entity
class EventState(

        @Id
        @Column(name = "id")
        override val id: UUID = UUID.randomUUID(),

        @Enumerated(EnumType.STRING)
        @Column(name = "state_type", nullable = false)
        val stateType: StateType = StateType.APPLYING,

        @Column(name = "state_date", nullable = false)
        val stateDate: ZonedDateTime = ZonedDateTime.now(),

        ) : UUIDBasedEntity(id) {

    override fun toString(): String {
        return "StateEvent(" +
                "id=$id, " +
                "stateType=$stateType, " +
                "stateDate=$stateDate)"
    }

    @Suppress("unused")
    enum class StateType {
        APPLYING,
        ENDED
    }

}