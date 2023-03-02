package ru.surf.testing.exception

import ru.surf.testing.exception.base.TestingException
import java.util.UUID

class TestingPhaseNotActiveException(

        private val eventId: UUID

) : TestingException("Testing phase for event is not in ACTIVE state. " +
                     "Passing tests only allowed for events with ACTIVE testing phase state.") {

    override fun getParams() = mapOf(
            "eventId" to eventId
    )

}
