package ru.surf.core.service

import ru.surf.core.entity.SurfEmployee
import java.util.UUID

interface SurfEmployeeService {

    fun getSurfEmployee(id: UUID): SurfEmployee

}