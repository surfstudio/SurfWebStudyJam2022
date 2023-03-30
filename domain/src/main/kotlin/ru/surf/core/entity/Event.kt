package ru.surf.core.entity

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*
import jakarta.persistence.*

@Table(name = "events")
@Entity
class Event(

        @Id
        @Column(name = "id")
        override val id: UUID = UUID.randomUUID(),

        @Column(name = "title", nullable = false, unique = true)
        var title: String = "",

        @Column(name = "description", nullable = false)
        var description: String = "",

        @Column(name = "candidates_number", nullable = false)
        var candidatesAmount: Int = 0,

        @Column(name = "trainees_number", nullable = false)
        var traineesAmount: Int = 0,

        @Column(name = "offers_number", nullable = false)
        var offersAmount: Int = 0,

        @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "event_initiator_id", referencedColumnName = "id")
        val eventInitiator: SurfEmployee? = null,

        @ManyToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.EAGER, targetEntity = EventTag::class)
        @Fetch(value = FetchMode.SUBSELECT)
        @JoinTable(name = "event_tags_events",
                joinColumns = [JoinColumn(name = "event_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "event_tag_id", referencedColumnName = "id")])
        var eventTags: Set<EventTag> = mutableSetOf(),

        @OneToMany(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, targetEntity = EventState::class)
        @Fetch(value = FetchMode.SUBSELECT)
        @JoinColumn(name = "event_id")
        var eventStates: Set<EventState> = mutableSetOf(),

        @Column(name = "report_file_id", nullable = true)
        var reportFileId: UUID? = UUID.randomUUID()

        ) : UUIDBasedEntity(id) {

    val currentState: EventState?
        get() = eventStates.maxByOrNull { it.stateDate }

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(" +
                "id = $id, " +
                "description = $description, " +
                "candidatesAmount = $candidatesAmount, " +
                "traineesAmount = $traineesAmount, " +
                "offersAmount = $offersAmount)"
    }

}