package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.surf.core.entity.Question
import java.util.UUID

interface QuestionRepository : JpaRepository<Question, UUID> {
}