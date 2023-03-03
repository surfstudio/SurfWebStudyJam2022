package ru.surf.testing.exception

import ru.surf.testing.exception.base.TestingException
import java.util.UUID

class NoSuchEventException(

        private val eventId: UUID

) : TestingException("TestTemplate for event $eventId with given id doesn't exist") {

    override fun getParams() = mapOf(
            "eventId" to eventId
    )

}
