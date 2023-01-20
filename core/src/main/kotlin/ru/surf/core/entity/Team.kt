package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*
import javax.persistence.*

@Table(name = "teams")
@Entity
class Team(

    @Id
    @Column(name = "id")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "about")
    var description: String? = null,

    @Column(name = "project_git_link")
    val projectGitLink: String? = null,

    @Column(name = "project_miro_link")
    val projectMiroLink: String? = null,

    @OneToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.EAGER)
    @JoinColumn(name = "mentor_id", referencedColumnName = "id")
    val mentor: SurfEmployee = SurfEmployee(),

    @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "team")
    val feedbacks: List<TeamFeedback> = emptyList(),

    ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , description = $description , projectGitLink = $projectGitLink , projectMiroLink = $projectMiroLink , mentor = $mentor )"
    }

}