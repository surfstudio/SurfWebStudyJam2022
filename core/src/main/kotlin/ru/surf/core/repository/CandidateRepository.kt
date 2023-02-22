package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.surf.core.entity.Candidate
import java.util.*

@Repository
interface CandidateRepository : JpaRepository<Candidate, UUID> {

    fun getCandidatesByEventId(@Param("event_id") eventId: UUID): List<Candidate>

}