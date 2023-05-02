package ru.surf.core.exception.defence

import ru.surf.core.enum.ExceptionType
import ru.surf.core.exception.base.CoreServiceException

class DefenceCreationFailedException: CoreServiceException() {
    val exceptionType = ExceptionType.SERVICE_EXCEPTION

    override val message: String
        get() = description

    public override val description: String = "DEFENCE CREATION FAILED"
}