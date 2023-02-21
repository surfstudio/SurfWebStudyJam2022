package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.surf.core.entity.QuestionVariant
import java.util.*

@Repository
interface QuestionVariantRepository : JpaRepository<QuestionVariant, UUID> {
}