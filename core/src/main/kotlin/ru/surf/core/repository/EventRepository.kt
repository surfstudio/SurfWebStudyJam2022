package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.surf.core.entity.Event
import ru.surf.core.entity.EventTag
import java.util.*

@Repository
interface EventRepository : JpaRepository<Event, UUID> {

    fun findByEventTagsIn(eventTags: Set<EventTag>): Event?

}