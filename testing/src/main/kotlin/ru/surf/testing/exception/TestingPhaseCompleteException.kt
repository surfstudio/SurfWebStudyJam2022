package ru.surf.testing.exception

import ru.surf.testing.exception.base.TestingException
import java.util.UUID

class TestingPhaseCompleteException(

        private val eventId: UUID

) : TestingException("Testing phase for event is already in COMPLETE state. " +
                     "No further states.") {

    override fun getParams() = mapOf(
            "eventId" to eventId
    )

}
