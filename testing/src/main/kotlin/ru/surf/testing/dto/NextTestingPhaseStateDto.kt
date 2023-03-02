package ru.surf.testing.dto

import ru.surf.testing.entity.TestingPhaseState
import java.util.UUID

data class NextTestingPhaseStateDto(
        val eventId: UUID,
        val state: TestingPhaseState
)
