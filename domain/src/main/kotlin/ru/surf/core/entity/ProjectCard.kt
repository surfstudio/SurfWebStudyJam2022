package ru.surf.core.entity

import jakarta.persistence.*
import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*

@Table(name = "projects_cards")
@Entity
class ProjectCard(

    @Id
    @Column(name = "id")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "title", nullable = false, unique = true)
    var title: String = "",

    @Column(name = "project_note", nullable = false, unique = true)
    var projectNote: String = "",

    @Version
    var version: Long = 0,

    @OneToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "project_info_id", referencedColumnName = "id", nullable = false)
    val projectInfo: ProjectInfo = ProjectInfo()

) : UUIDBasedEntity(id) {

    override fun toString(): String {
        return "ProjectCard(id=$id, title='$title', projectNote='$projectNote', version=$version)"
    }
}