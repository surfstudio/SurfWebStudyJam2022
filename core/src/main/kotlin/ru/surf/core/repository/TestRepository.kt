package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.surf.core.entity.Test
import java.util.UUID

interface TestRepository : JpaRepository<Test, UUID> {
}