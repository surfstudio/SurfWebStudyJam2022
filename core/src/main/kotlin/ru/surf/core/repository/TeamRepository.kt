package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.surf.core.entity.Team
import java.util.UUID

interface TeamRepository : JpaRepository<Team, UUID> {
}