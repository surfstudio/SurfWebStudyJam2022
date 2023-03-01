package ru.surf.testing.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.surf.testing.entity.Answer
import java.util.UUID

@Repository
interface AnswerRepository : JpaRepository<Answer, UUID>