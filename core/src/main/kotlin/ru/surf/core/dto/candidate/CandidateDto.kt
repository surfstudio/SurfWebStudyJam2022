package ru.surf.core.dto.candidate

import java.util.*

data class CandidateDto(
        val eventId: UUID,
        val firstName: String,
        val lastName: String,
        val university: String,
        val faculty: String,
        val course: String,
        val experience: String,
        val vcs: String,
        val cv: CandidateCV,
        val email: String,
        val telegram: String,
        val feedback: String,
) {
    data class CandidateCV(
        val fileId: UUID
    )
}
