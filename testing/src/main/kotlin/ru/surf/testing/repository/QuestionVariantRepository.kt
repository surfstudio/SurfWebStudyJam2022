package ru.surf.testing.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.surf.testing.entity.QuestionVariant
import java.util.*

@Repository
interface QuestionVariantRepository : JpaRepository<QuestionVariant, UUID>