package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.surf.core.entity.StatesEvents
import java.util.UUID

interface StatesEventsRepository : JpaRepository<StatesEvents, UUID> {
}