package ru.surf.core.kafkaEvents

import ru.surf.core.entity.Candidate

data class CandidateAppliedEvent(
    override val emailType: EmailType = EmailType.DEFAULT,
    override val emailTo: String,
    override val subject: String = "Вы зарегистрировались на стажировку в Surf",

    val candidate: Candidate
) : MailEvent {
    override fun params(): Map<String, *> {
        return mapOf(
            "eventName" to candidate.event.description,
            "firstName" to candidate.firstName,
            "lastName" to candidate.lastName
        )
    }
}
