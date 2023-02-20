package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@Table(name = "candidates", uniqueConstraints = [
        UniqueConstraint(columnNames = [
                "email",
                "event_id"
        ])
])
@Entity
class Candidate(

        @Id
        @Column(name = "id")
        override val id: UUID = UUID.randomUUID(),

        @Column(name = "first_name", nullable = false)
        val firstName: String = "",

        @Column(name = "last_name", nullable = false)
        val lastName: String = "",

        @Column(name = "university", nullable = false)
        val university: String = "",

        @Column(name = "faculty", nullable = false)
        val faculty: String = "",

        @Column(name = "course", nullable = false)
        val course: String = "",

        @Column(name = "experience", nullable = false)
        val experience: String = "",

        @Column(name = "vcs", nullable = false)
        val vcs: String = "",

        @Column(name = "cv_file_id", columnDefinition = "uuid", nullable = false)
        var cvFileId: UUID = UUID.randomUUID(),

        @Column(name = "email", nullable = false)
        val email: String = "",

        @Column(name = "telegram", nullable = false)
        val telegram: String = "",

        @Column(name = "feedback", nullable = false)
        val feedback: String = "",

        @Column(name = "applied_at", nullable = false)
        val appliedAt: ZonedDateTime = ZonedDateTime.now(),

        @Column(name = "is_approved", nullable = false)
        var isApproved: Boolean = false,

        @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
        var event: Event = Event(),

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
                        "cvFileId=$cvFileId, " +
                        "email='$email', " +
                        "telegram='$telegram', " +
                        "feedback='$feedback', " +
                        "appliedAt=$appliedAt, " +
                        "isApproved=$isApproved, " +
                        "event=$event)"
        }
}