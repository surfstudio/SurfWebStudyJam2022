package ru.surf.core.mapper.candidate.impl

import org.springframework.stereotype.Component
import ru.surf.core.dto.candidate.CandidateDto
import ru.surf.core.entity.Candidate
import ru.surf.core.entity.Event
import ru.surf.core.mapper.candidate.CandidateMapper

@Component
class CandidateMapperImpl : CandidateMapper {

    // todo временно, посмотреть mapstruct
    override fun convertFromCandidateDtoToCandidateEntity(candidateDto: CandidateDto, event: Event): Candidate = Candidate(
            firstName = candidateDto.firstName,
            lastName = candidateDto.lastName,
            university = candidateDto.university,
            faculty = candidateDto.faculty,
            course = candidateDto.course,
            experience = candidateDto.experience,
            vcs = candidateDto.vcs,
            cvFileId = candidateDto.cv.fileId,
            email = candidateDto.email,
            telegram = candidateDto.telegram,
            feedback = candidateDto.feedback,
            event = event
    )

}