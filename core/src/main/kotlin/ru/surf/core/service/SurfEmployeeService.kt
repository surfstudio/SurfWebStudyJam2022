package ru.surf.core.service

import ru.surf.core.dto.CredentialsDto
import ru.surf.core.entity.SurfEmployee
import java.util.UUID

interface SurfEmployeeService {

    fun create(surfEmployee: SurfEmployee): Pair<SurfEmployee, UUID>

    fun getSurfEmployee(id: UUID): SurfEmployee

    fun activateSurfEmployee(surfEmployee: SurfEmployee, credentialsDto: CredentialsDto): SurfEmployee
}