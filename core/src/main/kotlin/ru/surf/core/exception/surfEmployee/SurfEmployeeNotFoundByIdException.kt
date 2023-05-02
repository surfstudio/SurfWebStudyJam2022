package ru.surf.core.exception.surfEmployee

import ru.surf.core.exception.ExceptionType
import ru.surf.core.exception.base.CoreServiceException
import java.util.UUID

class SurfEmployeeNotFoundByIdException(val id: UUID) : CoreServiceException() {

    val exceptionType = ExceptionType.SERVICE_EXCEPTION

    override val message: String
        get() = description

    public override val description: String = "NO SURF_EMPLOYEE WITH ID $id"

}