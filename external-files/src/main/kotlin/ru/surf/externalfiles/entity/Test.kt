package ru.surf.externalfiles.entity

import ru.surf.externalfiles.entity.base.UUIDBasedEntity
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Table(name = "tests")
@Entity
class Test(

    @Id
    @Column(name = "id")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "link")
    val link: String? = null,

    @Column(name = "score")
    val score: Int? = null,

    @Column(name = "start_date")
    val startDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "end_date")
    val endDate: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    val candidate: Candidate = Candidate(),

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    val event: Event = Event(),

    @ManyToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinTable(name = "tests_questions",
        joinColumns = [JoinColumn(name = "test_id")],
        inverseJoinColumns = [JoinColumn(name = "question_id")])
    val questions: Set<Question> = mutableSetOf()

) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , link = $link , score = $score , startDate = $startDate , endDate = $endDate )"
    }

}