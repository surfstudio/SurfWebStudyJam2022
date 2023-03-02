package ru.surf.testing.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*

@Table(name = "candidate_info")
@Entity
class CandidateInfo(

        @Id
        @Column(name="id")
        override val id: UUID = UUID.randomUUID(),

        @Column(name = "event_id", columnDefinition = "uuid", nullable = false)
        val eventId: UUID = UUID.randomUUID()

) : UUIDBasedEntity(id) {

        override fun toString(): String {
                return "CandidateInfo(" +
                        "id=$id, " +
                        "eventId=$eventId)"
        }

}