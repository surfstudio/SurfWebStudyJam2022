package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.surf.core.entity.Trainee
import java.util.*

@Repository
interface TraineeRepository : JpaRepository<Trainee, UUID> {

    fun findAllByIdIn(ids: List<UUID>): List<Trainee>

}