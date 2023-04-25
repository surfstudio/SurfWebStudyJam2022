package ru.surf.report.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.surf.core.entity.Event
import ru.surf.core.entity.EventState
import ru.surf.core.entity.EventTag
import java.util.*


interface EventRepository : JpaRepository<Event, UUID> {
    @Query("select e.description from Event e where e.id = :eventId")
    fun getDescriptionById(@Param("eventId") eventId: UUID): String

    @Query("select e.eventStates from Event e where e.id = :eventId")
    fun getStatesById(@Param("eventId") eventId: UUID): Set<EventState>

    @Modifying(flushAutomatically = true)
    @Query("update events set report_file_id = :reportFileId where id = :eventId", nativeQuery = true)
    fun updateReportFileId(@Param("reportFileId") reportFileId: UUID, @Param("eventId") eventId: UUID)

    @Modifying(flushAutomatically = true)
    @Query("update events set candidates_report_file_id = :candidatesReportFileId where id = :eventId", nativeQuery = true)
    fun updateCandidatesReportFileId(@Param("candidatesReportFileId") candidatesReportFileId: UUID, @Param("eventId") eventId: UUID)

    @Query("select e.eventTags from Event e")
    fun getEventTags(eventId: UUID): Set<EventTag>
}