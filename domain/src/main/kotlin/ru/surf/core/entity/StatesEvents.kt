package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Table(name = "states_events")
@Entity
class StatesEvents(

        @Id
        @Column(name = "id")
        override val id: UUID = UUID.randomUUID(),

        @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "state_id")
        var stateType: StateType = StateType(),

        @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "event_id")
        var event: Event = Event(),

        @Column(name = "date")
        var date: LocalDate = LocalDate.now(),

        ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , date = $date )"
    }

}