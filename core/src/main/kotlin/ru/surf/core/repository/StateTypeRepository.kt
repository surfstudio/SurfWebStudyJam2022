package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.surf.core.entity.StateType
import java.util.UUID

interface StateTypeRepository : JpaRepository<StateType, UUID> {
}