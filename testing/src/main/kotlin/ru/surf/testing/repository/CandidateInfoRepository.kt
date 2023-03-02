package ru.surf.testing.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.surf.testing.entity.CandidateInfo
import java.util.*

@Repository
interface CandidateInfoRepository : JpaRepository<CandidateInfo, UUID> {

    fun findAllByEventId(eventId: UUID): List<CandidateInfo>

}