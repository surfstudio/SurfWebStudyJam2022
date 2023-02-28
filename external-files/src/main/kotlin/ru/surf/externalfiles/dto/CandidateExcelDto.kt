package ru.surf.externalfiles.dto

data class CandidateExcelDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val tags: Set<String>
)
