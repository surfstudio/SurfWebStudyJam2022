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

        @Column(name = "first_name")
        var firstName: String = "",

        @Column(name = "last_name")
        val lastName: String = "",

        @Column(name = "university")
        val university: String = "",

        @Column(name = "faculty")
        val faculty: String = "",

        @Column(name = "course")
        val course: String = "",

        @Column(name = "experience")
        val experience: String = "",

        @Column(name = "vcs")
        val vcs: String = "",

        @Column(name = "email")
        var email: String = "",

        @Column(name = "telegram")
        val telegram: String = "",

        @Column(name = "feedback")
        val feedback: String = "",

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
    override fun toString(): String {
        return "Candidate(" +
                "id=$id, " +
                "firstName='$firstName', " +
                "lastName='$lastName', " +
                "university='$university', " +
                "faculty='$faculty', " +
                "course='$course', " +
                "experience='$experience', " +
                "vcs='$vcs', " +
                "email='$email', " +
                "telegram='$telegram', " +
                "feedback='$feedback', " +
                "isNew=$isNew, " +
                "isApproved=$isApproved" +
                ")"
    }

}