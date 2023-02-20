package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@Table(name = "states_events", uniqueConstraints = [
    UniqueConstraint(columnNames = [
        "state_type",
        "event_id"
    ])
])
@Entity
class StateEvent(

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

    enum class StateType {
        APPLYING,
        CLOSED
    }

}