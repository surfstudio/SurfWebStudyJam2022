package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.surf.core.entity.Defence
import java.util.*

interface DefenceRepository : JpaRepository<Defence, UUID> {

    fun getDefenceByZoomMeetingId(zoomMeetingId: Long): Defence?

}