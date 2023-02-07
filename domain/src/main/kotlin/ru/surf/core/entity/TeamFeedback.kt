package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Table(name = "teams_feedbacks")
@Entity
class TeamFeedback(

        @Id
        @Column(name = "id")
        override val id: UUID = UUID.randomUUID(),

        @Column(name = "comment")
        var comment: String = "",

        @Column(name = "score")
        var score: Int = 0,

        @Column(name = "feedback_date")
        var date: LocalDate = LocalDate.now(),

        @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "mentor_id")
        var mentor: SurfEmployee = SurfEmployee(),

        @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "team_id")
        var team: Team = Team(),

        ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , comment = $comment , score = $score , date = $date )"
    }

}