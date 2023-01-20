package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.surf.core.entity.EventType
import java.util.UUID

interface EventTypeRepository : JpaRepository<EventType, UUID> {
}