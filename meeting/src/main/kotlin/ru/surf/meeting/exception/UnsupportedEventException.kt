package ru.surf.meeting.exception

import ru.surf.core.enum.ExceptionType

class UnsupportedEventException(val type: String) : MeetingServiceException() {

    val exceptionType = ExceptionType.SERVICE_EXCEPTION

    override val message: String
        get() = description

    public override val description: String = "UNSUPPORTED EVENT TYPE $type"

}