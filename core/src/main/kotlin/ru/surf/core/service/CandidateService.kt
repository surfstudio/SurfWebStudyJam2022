package ru.surf.core.service

import ru.surf.core.dto.CandidateApprovalDto
import ru.surf.core.dto.CandidateDto
import ru.surf.core.dto.CandidatePromotionDto
import ru.surf.core.entity.Account
import ru.surf.core.entity.Candidate
import ru.surf.externalfiles.dto.CandidateExcelDto
import java.util.UUID

interface CandidateService {

    fun createCandidate(candidateDto: CandidateDto): Candidate

    fun approveCandidate(candidate: Candidate): CandidateApprovalDto

    fun promoteCandidate(candidate: Candidate, candidatePromotionDto: CandidatePromotionDto): Account

    fun get(candidateId: UUID): Candidate

    fun getPreferredCandidates(eventId: UUID): Map<Candidate, List<String>>

    fun notifyCandidates(candidates: List<CandidateExcelDto>)

}