package ru.surf.core.service

import ru.surf.core.entity.Trainee
import java.util.UUID

interface TraineeService {

    fun getTrainee(id: UUID): Trainee

}