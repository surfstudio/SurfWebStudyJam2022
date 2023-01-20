package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.surf.core.entity.TeamFeedback
import java.util.UUID

interface TeamFeedbackRepository : JpaRepository<TeamFeedback, UUID> {
}