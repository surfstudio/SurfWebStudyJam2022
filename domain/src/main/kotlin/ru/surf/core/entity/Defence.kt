package ru.surf.core.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import ru.surf.core.entity.base.UUIDBasedEntity
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "defences")
class Defence(

    @Id
    @Column(name = "id")
    override var id: UUID = UUID.randomUUID(),

    @Column(name = "title")
    var title: String = "",

    @Column(name = "description")
    var description: String = "",

    @Column(name = "zoom_link")
    var zoomLink: String = "",

    @Column(name = "date_time")
    val date: LocalDateTime = LocalDateTime.now(),

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "zoom_meeting_id")
    var zoomMeetingId: Long = 0

) : UUIDBasedEntity(id) {

    override fun toString(): String {
        return "Defence(id=$id, title='$title', description='$description', zoomLink='$zoomLink', date=$date, createdAt=$createdAt, zoomMeetingId=$zoomMeetingId)"
    }
}