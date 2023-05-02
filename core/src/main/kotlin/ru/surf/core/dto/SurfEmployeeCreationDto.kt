package ru.surf.core.dto

import java.util.*

data class SurfEmployeeCreationDto(
        val surfEmployee: SurfEmployeeDto,
        val activationId: UUID
)
