package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*
import jakarta.persistence.*

@Table(name = "teams")
@Entity
class Team(

        @Id
        @Column(name = "id")
        override val id: UUID = UUID.randomUUID(),

        @Column(name = "title", nullable = false, unique = true)
        var title: String = "",

        @Column(name = "project_git_link", nullable = false, unique = true)
        var projectGitLink: String = "",

        @Column(name = "project_miro_link", nullable = false, unique = true)
        var projectMiroLink: String = "",

        @OneToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "mentor_id", referencedColumnName = "id", nullable = false)
        val mentor: SurfEmployee = SurfEmployee(),

        @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "event_id", referencedColumnName = "id")
        var event: Event = Event(),

        ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , title = $title , projectGitLink = $projectGitLink , projectMiroLink = $projectMiroLink , mentor = $mentor )"
    }

}