package ru.surf.testing.exception

import ru.surf.testing.exception.base.TestingException
import java.util.UUID

class BadAnswerException(

        private val testVariantId: UUID,
        private val candidateId: UUID,
        private val reason: String

) : TestingException("Invalid answer request") {

    override fun getParams() = mapOf(
            "testVariantId" to testVariantId,
            "candidateId" to candidateId,
            "reason" to reason
    )

}
