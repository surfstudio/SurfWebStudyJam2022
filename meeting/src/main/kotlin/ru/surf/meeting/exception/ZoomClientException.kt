package ru.surf.meeting.exception

import ru.surf.core.enum.ExceptionType

class ZoomClientException(private val details: String) : MeetingServiceException() {

    val exceptionType = ExceptionType.SERVICE_EXCEPTION

    override val message: String
        get() = description

    public override val description: String = "ZOOM CLIENT EXCEPTION OCCURRED:$details"
}