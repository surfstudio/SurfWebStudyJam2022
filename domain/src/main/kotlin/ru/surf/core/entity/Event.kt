package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*
import javax.persistence.*

@Table(name = "events")
@Entity
class Event(

        @Id
        @Column(name = "id")
        override val id: UUID = UUID.randomUUID(),

        @Column(name = "about")
        var description: String? = null,

        @Column(name = "candidates_number")
        var candidatesAmount: Int? = null,

        @Column(name = "trainees_number")
        var traineesAmount: Int? = null,

        @Column(name = "offers_number")
        var offersAmount: Int? = null,

        @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "event_type_id", referencedColumnName = "id")
        var eventType: EventType? = null,

        @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "event_initiator_id", referencedColumnName = "id")
        var eventInitiator: SurfEmployee? = null,

        @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "event")
        var trainees: MutableList<Trainee> = mutableListOf(),

        @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "event")
        var statesEvents: MutableList<StatesEvents> = mutableListOf(),

        ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , description = $description , candidatesAmount = $candidatesAmount , traineesAmount = $traineesAmount , offersAmount = $offersAmount )"
    }

}