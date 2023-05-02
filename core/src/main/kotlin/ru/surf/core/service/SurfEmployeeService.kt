package ru.surf.core.service

import ru.surf.core.dto.candidate.CredentialsDto
import ru.surf.core.entity.SurfEmployee
import ru.surf.core.entity.Team
import java.util.UUID

interface SurfEmployeeService {

    fun getSurfEmployee(id: UUID): SurfEmployee

    fun generateJuryForTeam(team: Team): List<SurfEmployee>

    fun activateSurfEmployee(surfEmployee: SurfEmployee, credentialsDto: CredentialsDto): SurfEmployee

    fun create(surfEmployee: SurfEmployee): Pair<SurfEmployee, UUID>
}