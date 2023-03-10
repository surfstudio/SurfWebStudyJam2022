package ru.surf.report.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.surf.core.entity.Event
import ru.surf.core.entity.EventState
import java.util.UUID

interface EventRepository : JpaRepository<Event, UUID> {
    @Query("select e.description from Event e where e.id = :eventId")
    fun getDescriptionById(@Param("eventId") eventId: UUID): String

    @Query("select e.eventStates from Event e where e.id = :eventId")
    fun getStatesById(@Param("eventId") eventId: UUID): Set<EventState>
}