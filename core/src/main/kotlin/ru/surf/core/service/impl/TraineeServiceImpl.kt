package ru.surf.core.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.surf.core.entity.Trainee
import ru.surf.core.exception.trainee.TraineeNotFoundByIdException
import ru.surf.core.repository.TraineeRepository
import ru.surf.core.service.TraineeService
import java.util.*
import kotlin.NoSuchElementException

@Service
class TraineeServiceImpl(private val traineeRepository: TraineeRepository) : TraineeService {

    override fun getTrainee(id: UUID): Trainee =
        traineeRepository.findByIdOrNull(id) ?: throw TraineeNotFoundByIdException(id)

}