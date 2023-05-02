package ru.surf.core.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.surf.core.dto.candidate.CredentialsDto
import ru.surf.core.dto.SurfEmployeeCreationDto
import ru.surf.core.dto.SurfEmployeeDto
import ru.surf.core.entity.Account
import ru.surf.core.entity.SurfEmployee
import ru.surf.core.mapper.surfEmployee.SurfEmployeeMapper
import ru.surf.core.service.SurfEmployeeService

@RestController
@RequestMapping(value = ["/surf_employee"])
class SurfEmployeeController(

        @Autowired
        private val surfEmployeeService: SurfEmployeeService,

        @Autowired
        private val surfEmployeeMapper: SurfEmployeeMapper,

) {

    @PostMapping(value = ["/"])
    fun create(@RequestBody surfEmployeeDto: SurfEmployeeDto): ResponseEntity<SurfEmployeeCreationDto> =
            surfEmployeeService.create(
                    surfEmployeeMapper.toEntity(surfEmployeeDto)
            ).run {
                ResponseEntity.ok(
                        SurfEmployeeCreationDto(
                                surfEmployee = surfEmployeeMapper.toDto(first),
                                activationId = second
                        )
                )
            }

    @PostMapping(value = ["/activate"])
    @PreAuthorize("isAuthenticated")
    fun activate(@AuthenticationPrincipal surfEmployee: SurfEmployee,
                 @RequestBody credentialsDto: CredentialsDto): Account =
            surfEmployeeService.activateSurfEmployee(surfEmployee, credentialsDto)

}