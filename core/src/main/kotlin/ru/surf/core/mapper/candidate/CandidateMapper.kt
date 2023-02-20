package ru.surf.core.mapper.candidate

import ru.surf.core.dto.CandidateDto
import ru.surf.core.entity.Candidate
import ru.surf.core.entity.Event

interface CandidateMapper {

    // todo временно, посмотреть mapstruct
    fun convertFromCandidateDtoToCandidateEntity(candidateDto: CandidateDto, event: Event): Candidate

}