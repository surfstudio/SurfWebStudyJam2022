package ru.surf.testing.exception

import ru.surf.testing.exception.base.TestingException
import java.util.UUID

class TestStartedException(

        private val testVariantId: UUID,
        private val candidateId: UUID

) : TestingException("Test has already been created and started") {

    override fun getParams() = mapOf(
            "testVariantId" to testVariantId,
            "candidateId" to candidateId
    )

}
