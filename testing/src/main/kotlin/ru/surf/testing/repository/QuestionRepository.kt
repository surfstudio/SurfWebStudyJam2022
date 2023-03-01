package ru.surf.testing.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.surf.testing.entity.Question
import java.util.UUID

@Repository
interface QuestionRepository : JpaRepository<Question, UUID>