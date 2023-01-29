package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*
import javax.persistence.*

@Table(name = "candidates")
@Entity
class Candidate(

    @Id
    @Column(name = "id")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "name")
    var name: String = "",

    @Column(name = "email")
    var email: String = "",

    @Column(name = "is_new")
    var isNew: Boolean? = null,

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "hr_from_id", referencedColumnName = "id")
    var hr: SurfEmployee = SurfEmployee(),

    @ManyToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinTable(
        name = "candidates_events",
        joinColumns = [JoinColumn(name = "event_id")],
        inverseJoinColumns = [JoinColumn(name = "candidate_id")]
    )
    var events: MutableSet<Event> = mutableSetOf(),

    ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , email = $email , isNew = $isNew )"
    }

}