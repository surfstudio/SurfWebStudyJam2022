package ru.surf.core.kafkaEvents

import ru.surf.testing.entity.TestVariant

data class TestCreatedEvent(
        override val emailTo: String,
        val testVariant: TestVariant
) : IMailEvent {
    override fun convertToParam(): Map<*, *> =
            mapOf(
                    "firstName" to testVariant.candidateInfo.firstName,
                    "lastName" to testVariant.candidateInfo.lastName,
                    "testLink" to testVariant.id,
                    "deadline" to testVariant.testTemplate.eventInfo.expectedTestingPhaseDeadline,
            )
}
