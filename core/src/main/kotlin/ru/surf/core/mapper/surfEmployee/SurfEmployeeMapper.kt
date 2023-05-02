package ru.surf.core.mapper.surfEmployee

import ru.surf.core.dto.SurfEmployeeDto
import ru.surf.core.entity.SurfEmployee

interface SurfEmployeeMapper {

    fun toEntity(surfEmployeeDto: SurfEmployeeDto): SurfEmployee

    fun toDto(surfEmployee: SurfEmployee): SurfEmployeeDto

}