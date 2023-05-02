package ru.surf.core.service

import ru.surf.core.dto.CandidateApprovalDto
import ru.surf.core.dto.CandidateDto
import ru.surf.core.entity.Candidate
import ru.surf.externalfiles.dto.CandidateExcelDto
import java.util.UUID

interface CandidateService {

    fun createCandidate(candidateDto: CandidateDto): Candidate

    fun approveCandidate(candidate: Candidate): CandidateApprovalDto

    fun get(candidateId: UUID): Candidate

    fun getAllByEventId(eventId: UUID): List<Candidate>

    fun getPreferredCandidates(eventId: UUID): Map<Candidate, List<String>>

    fun notifyCandidates(candidates: List<CandidateExcelDto>)

}