package ru.surf.externalfiles.exception

import ru.surf.core.enum.ExceptionType
import ru.surf.externalfiles.exception.base.ExternalFileServiceException
import java.util.*

class S3FileNotFoundException(fileId: UUID) : ExternalFileServiceException() {

    val exceptionType = ExceptionType.SERVICE_EXCEPTION

    override val message: String
        get() = description

    public override val description: String = "NO FILE WITH id $fileId"

}