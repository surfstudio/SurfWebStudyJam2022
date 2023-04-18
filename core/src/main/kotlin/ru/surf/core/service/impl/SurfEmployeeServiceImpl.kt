package ru.surf.core.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.surf.core.entity.SurfEmployee
import ru.surf.core.entity.Team
import ru.surf.core.exception.surfEmployee.SurfEmployeeNotFoundByIdException
import ru.surf.core.repository.SurfEmployeeRepository
import ru.surf.core.service.SurfEmployeeService
import java.util.*

@Service
class SurfEmployeeServiceImpl(private val surfEmployeeRepository: SurfEmployeeRepository) : SurfEmployeeService {

    override fun getSurfEmployee(id: UUID): SurfEmployee =
            surfEmployeeRepository.findByIdOrNull(id) ?: throw SurfEmployeeNotFoundByIdException(id)

    // TODO: 16.03.2023 Включить поиск для hr как будет готово
    override fun generateJuryForTeam(team: Team): List<SurfEmployee> =
            surfEmployeeRepository.findMentors()
                    .filter { it.id != team.mentor.id }.shuffled().take(3).toList()


}