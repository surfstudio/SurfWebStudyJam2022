package ru.surf.core.exception.defence

import ru.surf.core.enum.ExceptionType
import ru.surf.core.exception.base.CoreServiceException
import java.util.UUID

class DefenceNotFoundByIdException(private val id: UUID) : CoreServiceException() {
    val exceptionType = ExceptionType.SERVICE_EXCEPTION

    override val message: String
    get() = description

    public override val description: String = "NO DEFENCE WITH ID $id"
}