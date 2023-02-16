package ru.surf.core.kafkaEvents

import ru.surf.core.entity.Candidate

data class CandidateAppliedEvent(
        override val emailTo: String,
        val candidate: Candidate
) : IMailEvent {
    override fun convertToParam(): Map<*, *> =
            mapOf(
                    "eventName" to candidate.events.first().description,
                    "firstName" to candidate.firstName,
                    "lastName" to candidate.lastName
            )
}
