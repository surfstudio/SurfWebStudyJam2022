package ru.surf.core.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.surf.core.entity.SurfEmployee
import ru.surf.core.repository.SurfEmployeeRepository
import ru.surf.core.service.SurfEmployeeService
import java.util.*

@Service
class SurfEmployeeServiceImpl(private val surfEmployeeRepository: SurfEmployeeRepository) : SurfEmployeeService {

    override fun getSurfEmployee(id: UUID): SurfEmployee =
        surfEmployeeRepository.findByIdOrNull(id) ?: throw NoSuchElementException()

}