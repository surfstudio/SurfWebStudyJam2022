package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.surf.core.entity.AnswerVariant
import java.util.*

@Repository
interface AnswerVariantRepository : JpaRepository<AnswerVariant, UUID> {
}