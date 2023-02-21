package ru.surf.core.service

import ru.surf.core.entity.Candidate

interface CandidateFilterService {

    fun filterCandidatesForm(candidates: List<Candidate>): Map<Candidate, List<String>>

}