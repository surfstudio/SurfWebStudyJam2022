package ru.surf.report.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.surf.core.entity.Trainee
import java.util.UUID

interface TraineeRepository : JpaRepository<Trainee, UUID> {
    @Query("select count(t) from Trainee t where t.team.event.id = :eventId")
    fun countByEventId(@Param("eventId") eventId: UUID): Int
    @Query("select t.id from Trainee t where t.candidate.event.id = :eventId")
    fun getAllId(eventId: UUID) : List<UUID>
}