package ru.surf.testing.entity

import jakarta.persistence.*
import ru.surf.core.entity.base.UUIDBasedEntity
import ru.surf.core.validation.EmailConstraint
import java.util.*

@Table(name = "candidates_info")
@Entity
class CandidateInfo(

        @Id
        @Column(name="id")
        override val id: UUID = UUID.randomUUID(),

        @Column(name = "first_name", nullable = false)
        var firstName: String = "",

        @Column(name = "last_name", nullable = false)
        var lastName: String = "",

        @Column(name = "email", nullable = false)
        @EmailConstraint(regexp = ".+?@.+")
        var email: String = "",

        @OneToOne(cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST], fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
        val eventInfo: EventInfo = EventInfo(),


) : UUIDBasedEntity(id) {

        override fun toString(): String {
                return "CandidateInfo(" +
                        "id=$id, " +
                        "firstName='$firstName', " +
                        "lastName='$lastName', " +
                        "eventId=${eventInfo.id})"
        }
}