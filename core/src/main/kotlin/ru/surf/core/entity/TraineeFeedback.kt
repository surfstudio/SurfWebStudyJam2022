package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Table(name = "trainees_feedbacks")
@Entity
class TraineeFeedback(

    @Id
    @Column(name = "id")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "score")
    var score: Int = 0,

    @Column(name = "comment")
    var comment: String = "",

    @Column(name = "date")
    var date: LocalDate = LocalDate.now(),

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "surf_employee_id")
    val ownerFeedback: SurfEmployee = SurfEmployee(),

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "trainee_id")
    val traineeReceiver: Trainee = Trainee()

    ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , score = $score , comment = $comment , date = $date )"
    }

}