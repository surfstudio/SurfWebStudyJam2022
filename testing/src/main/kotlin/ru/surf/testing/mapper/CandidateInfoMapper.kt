package ru.surf.testing.mapper

import ru.surf.core.entity.Candidate
import ru.surf.testing.dto.CandidateInfoFullDto
import ru.surf.testing.entity.CandidateInfo

interface CandidateInfoMapper {

    fun toEntity(candidateInfoFullDto: CandidateInfoFullDto): CandidateInfo

    fun toEntity(candidate: Candidate): CandidateInfo

}