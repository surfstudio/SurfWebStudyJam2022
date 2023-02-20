package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@Table(name = "teams_feedbacks", uniqueConstraints = [
    UniqueConstraint(columnNames = [
        "surf_employee_id",
        "team_id"
    ])
])
@Entity
class TeamFeedback(

        @Id
        @Column(name = "id")
        override val id: UUID = UUID.randomUUID(),

        @Column(name = "comment", nullable = false)
        val comment: String = "",

        @Column(name = "score", nullable = false)
        val score: Int = 0,

        @Column(name = "feedback_date", nullable = false)
        val feedbackDate: ZonedDateTime = ZonedDateTime.now(),

        @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "team_id", nullable = false)
        val team: Team = Team(),

        @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "surf_employee_id", nullable = false)
        val employee: SurfEmployee = SurfEmployee(),

        ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , comment = $comment , score = $score , date = $feedbackDate )"
    }

}