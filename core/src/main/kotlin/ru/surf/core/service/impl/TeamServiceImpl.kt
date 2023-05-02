package ru.surf.core.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.surf.core.entity.Team
import ru.surf.core.exception.team.TeamNotFoundByIdException
import ru.surf.core.repository.TeamRepository
import ru.surf.core.service.TeamService
import java.util.*

@Service
class TeamServiceImpl(private val teamRepository: TeamRepository) : TeamService {

    override fun getTeam(id: UUID): Team =
        teamRepository.findByIdOrNull(id) ?: throw TeamNotFoundByIdException(id)

}