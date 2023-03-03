package ru.surf.core.kafkaEvents

import ru.surf.testing.entity.CandidateInfo

data class TestPassedEvent(
        override val emailTo: String,
        val candidateInfo: CandidateInfo
) : IMailEvent {
    override fun convertToParam(): Map<*, *> =
            mapOf(
                    "firstName" to candidateInfo.firstName,
                    "lastName" to candidateInfo.lastName,
            )
}
