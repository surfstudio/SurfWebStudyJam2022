package ru.surf.externalfiles.entity

import ru.surf.externalfiles.entity.base.UUIDBasedEntity
import java.util.*
import javax.persistence.*

@Table(name = "surf_employees")
@Entity
class SurfEmployee(

    @Id
    @Column(name = "id")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "name")
    var name: String = "",

    @OneToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    val account: Account = Account(),

    @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "eventInitiator")
    val events: List<Event> = emptyList(),

    @ManyToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinTable(name = "surf_employees_s3files",
        joinColumns = [JoinColumn(name = "surf_employee_id")],
        inverseJoinColumns = [JoinColumn(name = "s3file_id")])
    val s3files: List<S3File> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "ownerFeedback")
    val feedbacksForTrainee: List<TraineeFeedback> = emptyList(),

    @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "mentor")
    val feedbacksForTeam: List<TeamFeedback> = emptyList(),

    ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , account = $account )"
    }

}