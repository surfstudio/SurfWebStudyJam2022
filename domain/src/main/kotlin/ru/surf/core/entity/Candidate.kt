package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.time.ZonedDateTime
import java.util.*
import jakarta.persistence.*
import ru.surf.core.validation.EmailConstraint

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
        @EmailConstraint(regexp = ".+?@.+")
        val email: String = "",

        @Column(name = "telegram", nullable = false)
        val telegram: String = "",

        @Column(name = "feedback", nullable = false)
        val feedback: String = "",

        @Column(name = "applied_at", nullable = false)
        val appliedAt: ZonedDateTime = ZonedDateTime.now(),

        @Column(name = "is_approved", nullable = false)
        var approved: Boolean = false,

        @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.EAGER)
        @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
        val event: Event = Event(),

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
                        "isApproved=$approved, " +
                        "event=${event})"
        }
}