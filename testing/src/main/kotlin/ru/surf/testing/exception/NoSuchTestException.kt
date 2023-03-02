package ru.surf.testing.exception

import ru.surf.testing.exception.base.TestingException
import java.util.UUID

class NoSuchTestException(

        private val testVariantId: UUID

) : TestingException("TestVariant with given id doesn't exist") {

    override fun getParams() = mapOf(
            "testVariantId" to testVariantId
    )

}
