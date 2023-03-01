package ru.surf.testing.exception

import ru.surf.testing.entity.TestVariant
import ru.surf.testing.exception.base.TestingException
import java.util.UUID

class TestNotStartedException(

        private val testVariantId: UUID,
        private val candidateId: UUID,
        private val testState: TestVariant.TestState

) : TestingException("This test has either been not started or already finished") {

    override fun getParams() = mapOf(
            "testVariantId" to testVariantId,
            "candidateId" to candidateId,
            "testState" to testState
    )

}
