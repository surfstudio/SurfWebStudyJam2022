package ru.surf.testing.exception

import ru.surf.testing.exception.base.TestingException
import java.util.UUID

class NoSuchCandidateException(

        private val candidateId: UUID

) : TestingException("TestVariant for candidate with given id doesn't exist") {

    override fun getParams() = mapOf(
            "candidateId" to candidateId
    )

}
