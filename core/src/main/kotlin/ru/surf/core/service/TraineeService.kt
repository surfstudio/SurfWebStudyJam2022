package ru.surf.core.service

import ru.surf.core.dto.candidate.CredentialsDto
import ru.surf.core.entity.Candidate
import ru.surf.core.entity.Trainee
import java.util.UUID

interface TraineeService {

    fun getTrainee(id: UUID): Trainee


    fun activateCandidate(candidate: Candidate, credentialsDto: CredentialsDto): Trainee

}