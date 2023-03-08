package ru.surf.core.mapper.surfEmployee.impl

import org.springframework.stereotype.Component
import ru.surf.core.dto.SurfEmployeeDto
import ru.surf.core.entity.SurfEmployee
import ru.surf.core.mapper.surfEmployee.SurfEmployeeMapper

@Component
class SurfEmployeeMapperImpl : SurfEmployeeMapper {
    override fun toEntity(surfEmployeeDto: SurfEmployeeDto): SurfEmployee = SurfEmployee(
            email = surfEmployeeDto.email,
            role = surfEmployeeDto.role.mapToAccountRole(),
            name = surfEmployeeDto.name
    )

    override fun toDto(surfEmployee: SurfEmployee): SurfEmployeeDto = SurfEmployeeDto(
            email = surfEmployee.email,
            role = SurfEmployeeDto.SurfEmployeeRole.mapFromEntityRole(surfEmployee.role),
            name = surfEmployee.name,
    )
}