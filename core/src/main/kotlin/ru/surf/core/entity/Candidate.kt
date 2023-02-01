package ru.surf.core.entity

import com.fasterxml.jackson.annotation.JsonIgnore
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

    @Column(name = "is_approved")
    var isApproved: Boolean = false,

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "hr_from_id", referencedColumnName = "id")
    val hr: SurfEmployee? = null,

    @ManyToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.EAGER)
    @JoinTable(name = "candidates_events",
        joinColumns = [JoinColumn(name = "event_id")],
        inverseJoinColumns = [JoinColumn(name = "candidate_id")])
    @JsonIgnore
    val events: MutableSet<Event> = mutableSetOf(),

    @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "candidate")
    @JsonIgnore
    val trainees: List<Trainee> = emptyList(),

    ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , email = $email , isNew = $isNew )"
    }

}