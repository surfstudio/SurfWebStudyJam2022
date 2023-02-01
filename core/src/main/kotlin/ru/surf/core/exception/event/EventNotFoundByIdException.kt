package ru.surf.core.exception.event

import ru.surf.core.exception.ExceptionType
import ru.surf.core.exception.base.CoreServiceException
import java.util.UUID

class EventNotFoundByIdException(val id: UUID) : CoreServiceException() {

    val exceptionType = ExceptionType.SERVICE_EXCEPTION

    override val message: String
        get() = description

    public override val description: String = "NO EVENT WITH ID $id"

}