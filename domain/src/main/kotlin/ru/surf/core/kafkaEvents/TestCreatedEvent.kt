package ru.surf.core.kafkaEvents

import ru.surf.testing.entity.TestVariant

data class TestCreatedEvent(
        override val emailType: EmailType = EmailType.DEFAULT,
        override val emailTo: String,
        override val subject: String = "Тест для стажировки Surf",

        val testVariant: TestVariant,
) : MailEvent {
    override fun params(): Map<String, *> =
            mapOf(
                    "firstName" to testVariant.candidateInfo.firstName,
                    "lastName" to testVariant.candidateInfo.lastName,
                    "testId" to testVariant.id,
                    "deadline" to testVariant.testTemplate.eventInfo.expectedTestingPhaseDeadline,
            )
}
