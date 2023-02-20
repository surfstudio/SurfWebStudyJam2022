package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@Table(name = "trainees_feedbacks", uniqueConstraints = [
    UniqueConstraint(columnNames = [
        "surf_employee_id",
        "trainee_id"
    ])
])
@Entity
class TraineeFeedback(

        @Id
        @Column(name = "id")
        override val id: UUID = UUID.randomUUID(),

        @Column(name = "score", nullable = false)
        val score: Int = 0,

        @Column(name = "comment", nullable = false)
        val comment: String = "",

        @Column(name = "feedback_date", nullable = false)
        val feedbackDate: ZonedDateTime = ZonedDateTime.now(),

        @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "trainee_id", nullable = false)
        val trainee: Trainee = Trainee(),

        @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "surf_employee_id", nullable = false)
        val employee: SurfEmployee = SurfEmployee(),

        ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , score = $score , comment = $comment , date = $feedbackDate )"
    }

}