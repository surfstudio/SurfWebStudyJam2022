package ru.surf.core.exception.trainee

import ru.surf.core.exception.ExceptionType
import ru.surf.core.exception.base.CoreServiceException
import java.util.UUID

class TraineeNotFoundByIdException(val id: UUID) : CoreServiceException() {

    val exceptionType = ExceptionType.SERVICE_EXCEPTION

    override val message: String
        get() = description

    public override val description: String = "NO TRAINEE WITH ID $id"
}