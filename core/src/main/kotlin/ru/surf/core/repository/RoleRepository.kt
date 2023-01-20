package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.surf.core.entity.Role
import java.util.UUID

interface RoleRepository : JpaRepository<Role, UUID> {
}