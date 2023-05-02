package ru.surf.core.exception.team

import ru.surf.core.exception.ExceptionType
import ru.surf.core.exception.base.CoreServiceException
import java.util.UUID

class TeamNotFoundByIdException(val id: UUID) : CoreServiceException() {

    val exceptionType = ExceptionType.SERVICE_EXCEPTION

    override val message: String
        get() = description

    public override val description: String = "NO TEAM WITH ID $id"
}