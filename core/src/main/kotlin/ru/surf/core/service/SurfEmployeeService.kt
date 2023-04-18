package ru.surf.core.service

import ru.surf.core.entity.SurfEmployee
import ru.surf.core.entity.Team
import java.util.UUID

interface SurfEmployeeService {

    fun getSurfEmployee(id: UUID): SurfEmployee

    fun generateJuryForTeam(team: Team): List<SurfEmployee>
}