package ru.surf.core.kafkaEvents

import ru.surf.testing.entity.CandidateInfo

data class TestPassedEvent(
        override val emailType: EmailType = EmailType.DEFAULT,
        override val emailTo: String,
        override val subject: String = "Вы написали тест Surf",

        val candidateInfo: CandidateInfo
) : IMailEvent {
    override fun params(): Map<String, *> =
            mapOf(
                    "firstName" to candidateInfo.firstName,
                    "lastName" to candidateInfo.lastName,
            )
}
