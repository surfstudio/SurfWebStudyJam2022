package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.surf.core.entity.EventState
import java.util.UUID

@Repository
interface StatesEventsRepository : JpaRepository<EventState, UUID> {
}