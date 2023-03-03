package ru.surf.testing.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.surf.testing.entity.TestVariant
import java.util.*

@Repository
interface TestVariantRepository : JpaRepository<TestVariant, UUID> {

    fun findByCandidateInfoId(candidateId: UUID): TestVariant?

    fun findAllByTestTemplateEventInfoId(eventId: UUID): List<TestVariant>

}