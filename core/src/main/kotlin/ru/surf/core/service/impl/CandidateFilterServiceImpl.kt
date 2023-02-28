package ru.surf.core.service.impl

import org.springframework.stereotype.Service
import ru.surf.core.entity.Candidate
import ru.surf.core.service.CandidateFilterService
import ru.surf.core.util.KeywordsPath
import ru.surf.core.util.readFileStrings

@Service
class CandidateFilterServiceImpl : CandidateFilterService {

    val keywords = readFileStrings(KeywordsPath.JAVA_BACKEND_KEYWORDS_PATH.path)

    override fun filterCandidatesForm(candidates: List<Candidate>): Map<Candidate, List<String>> =
         candidates.associateWithTo(mutableMapOf()) { candidate -> candidate.experience.split(" ") }
            .mapValues { matchNeedWords(it.value) }

    private fun matchNeedWords(candidatesSkills: List<String>): List<String> {
        return candidatesSkills.filter { keywords.contains(it) }.toList()
    }

}