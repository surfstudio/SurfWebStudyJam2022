package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.surf.core.entity.Candidate
import java.util.UUID

interface CandidateRepository : JpaRepository<Candidate, UUID> {
}