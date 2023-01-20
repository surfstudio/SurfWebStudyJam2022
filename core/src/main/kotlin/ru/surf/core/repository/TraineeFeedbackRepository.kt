package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.surf.core.entity.TraineeFeedback
import java.util.*

interface TraineeFeedbackRepository : JpaRepository<TraineeFeedback, UUID> {
}