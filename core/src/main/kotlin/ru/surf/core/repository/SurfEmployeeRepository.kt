package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.surf.core.entity.SurfEmployee
import java.util.UUID

interface SurfEmployeeRepository : JpaRepository<SurfEmployee, UUID> {
}