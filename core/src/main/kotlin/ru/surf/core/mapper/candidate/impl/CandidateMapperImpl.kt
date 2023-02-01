package ru.surf.core.mapper.candidate.impl

import org.springframework.stereotype.Component
import ru.surf.core.dto.CandidateDto
import ru.surf.core.entity.Candidate
import ru.surf.core.mapper.candidate.CandidateMapper

@Component
class CandidateMapperImpl : CandidateMapper {

    override fun convertFromCandidateDtoToCandidateEntity(candidateDto: CandidateDto): Candidate = Candidate(
            name = candidateDto.name,
            email = candidateDto.email
    )

}