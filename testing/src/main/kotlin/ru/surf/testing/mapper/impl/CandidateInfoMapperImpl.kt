package ru.surf.testing.mapper.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.surf.core.entity.Candidate
import ru.surf.testing.dto.CandidateInfoFullDto
import ru.surf.testing.entity.CandidateInfo
import ru.surf.testing.mapper.CandidateInfoMapper
import ru.surf.testing.service.EventInfoService

@Component
class CandidateInfoMapperImpl(

        @Autowired
        private val eventInfoService: EventInfoService

) : CandidateInfoMapper {

    override fun toEntity(candidateInfoFullDto: CandidateInfoFullDto): CandidateInfo = CandidateInfo(
            id=candidateInfoFullDto.candidateId,
            firstName=candidateInfoFullDto.firstName,
            lastName=candidateInfoFullDto.lastName,
            email=candidateInfoFullDto.email,
            eventInfo=eventInfoService.getById(candidateInfoFullDto.eventId)
    )

    override fun toEntity(candidate: Candidate): CandidateInfo = CandidateInfo(
            id=candidate.id,
            firstName=candidate.firstName,
            lastName=candidate.lastName,
            email=candidate.email,
            eventInfo=eventInfoService.getById(candidate.event.id)
    )

}