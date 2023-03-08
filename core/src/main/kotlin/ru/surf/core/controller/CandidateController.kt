package ru.surf.core.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import ru.surf.core.dto.CandidateApprovalDto
import ru.surf.core.dto.CandidateDto
import ru.surf.core.entity.Candidate
import ru.surf.core.service.CandidateService
import java.util.*

@RestController
@RequestMapping(value = ["/candidates"])
class CandidateController(
        @Autowired
        private val candidateService: CandidateService
) {

    @PostMapping(value = ["/"])
    fun create(@RequestBody candidateDto: CandidateDto): Candidate = candidateService.createCandidate(candidateDto)

    @PostMapping(value = ["/approve/{candidateId}"])
    fun approve(@PathVariable candidateId: UUID): CandidateApprovalDto =
            candidateService.get(candidateId).let { candidateService.approveCandidate(it) }

    @GetMapping
    @PreAuthorize("isAuthenticated")
    fun get(@AuthenticationPrincipal candidate: Candidate): Candidate =
            candidate

}
