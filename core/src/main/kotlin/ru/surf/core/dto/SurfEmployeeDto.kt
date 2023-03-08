package ru.surf.core.dto

import ru.surf.core.entity.Account

data class SurfEmployeeDto(
        val email: String,
        val role: SurfEmployeeRole,
        val name: String,
) {
    enum class SurfEmployeeRole {
        SURF_EMPLOYEE {
            override fun mapToAccountRole(): Account.Role = Account.Role.SURF_EMPLOYEE
        };

        abstract fun mapToAccountRole(): Account.Role

        companion object {
            fun mapFromEntityRole(role: Account.Role): SurfEmployeeRole = when (role) {
                Account.Role.SURF_EMPLOYEE -> SURF_EMPLOYEE
                Account.Role.TRAINEE -> throw RuntimeException("Unable to map trainee role to surfEmployee role")
            }
        }
    }
}
