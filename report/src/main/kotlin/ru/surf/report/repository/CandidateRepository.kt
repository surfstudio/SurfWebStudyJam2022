package ru.surf.report.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.surf.core.entity.Candidate
import java.util.UUID


interface CandidateRepository : JpaRepository<Candidate, UUID> {
    @Query("select count(c) from Candidate c where c.event.id = :eventId")
    fun countByEventId(@Param("eventId") eventId: UUID): Int

    @Query("select c from Candidate c where c.id not in (:idList) and c.approved and c.event.id = :eventId")
    fun getApprovedFailedCandidatesByIdList(idList: List<UUID>, eventId: UUID): List<Candidate>

    @Query("select c from Candidate c where c.approved and c.event.id = :eventId")
    fun getApprovedFailedCandidates(eventId: UUID): List<Candidate>
}