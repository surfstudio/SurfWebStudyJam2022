package ru.surf.testing.exception

import ru.surf.testing.exception.base.TestingException
import java.util.UUID

class TestingPhaseActiveException(

        private val eventId: UUID

) : TestingException("Testing phase for event has entered to ACTIVE state. " +
                     "Overwriting test templates for this state is prohibited.") {

    override fun getParams() = mapOf(
            "eventId" to eventId
    )

}
