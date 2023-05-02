package ru.surf.core.service

import ru.surf.core.entity.Team
import java.util.UUID

interface TeamService {

    fun getTeam(id: UUID): Team

}