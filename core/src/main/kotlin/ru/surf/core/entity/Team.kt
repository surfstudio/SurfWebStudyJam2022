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
    var projectGitLink: String? = null,

    @Column(name = "project_miro_link")
    var projectMiroLink: String? = null,

    @OneToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.EAGER)
    @JoinColumn(name = "mentor_id", referencedColumnName = "id")
    var mentor: SurfEmployee = SurfEmployee(),

    @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "team")
    var feedbacks: MutableList<TeamFeedback> = mutableListOf(),

    ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , description = $description , projectGitLink = $projectGitLink , projectMiroLink = $projectMiroLink , mentor = $mentor )"
    }

}