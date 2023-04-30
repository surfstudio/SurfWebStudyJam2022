package ru.surf.core.entity

import jakarta.persistence.*
import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*

@Table(name = "projects_info")
@Entity
class ProjectInfo(

    @Id
    @Column(name = "id")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "git_link", nullable = false, unique = true)
    var gitLink: String = "",

    @Column(name = "miro_link", nullable = false, unique = true)
    var miroLink: String = "",

    @Column(name = "trello_link", nullable = false, unique = true)
    var trelloLink: String = "",

    @Column(name = "google_drive_link", nullable = false, unique = true)
    var googleDriveLink: String = "",

    @Column(name = "useful_resources_link", nullable = false, unique = true)
    var usefulResourcesLink: String = "",

    ) : UUIDBasedEntity(id) {

    override fun toString(): String {
        return "ProjectInfo(id=$id, gitLink='$gitLink', miroLink='$miroLink', trelloLink='$trelloLink', googleDriveLink='$googleDriveLink', usefulResourcesLink='$usefulResourcesLink')"
    }
}
