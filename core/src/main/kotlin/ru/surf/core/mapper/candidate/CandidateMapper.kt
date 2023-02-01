package ru.surf.core.mapper.candidate

import ru.surf.core.dto.CandidateDto
import ru.surf.core.entity.Candidate

interface CandidateMapper {

    fun convertFromCandidateDtoToCandidateEntity(candidateDto: CandidateDto): Candidate

}