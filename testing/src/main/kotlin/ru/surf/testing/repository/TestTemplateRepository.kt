package ru.surf.testing.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.surf.testing.entity.TestTemplate
import java.util.UUID

@Repository
interface TestTemplateRepository : JpaRepository<TestTemplate, UUID> {

    fun findByEventInfoId(eventId: UUID): TestTemplate?

}