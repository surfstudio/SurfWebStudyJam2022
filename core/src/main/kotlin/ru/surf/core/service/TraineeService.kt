package ru.surf.core.service

import ru.surf.core.dto.CredentialsDto
import ru.surf.core.entity.Candidate
import ru.surf.core.entity.Trainee

interface TraineeService {

    fun activateCandidate(candidate: Candidate, credentialsDto: CredentialsDto): Trainee

}