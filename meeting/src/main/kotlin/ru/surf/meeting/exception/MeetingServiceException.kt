package ru.surf.meeting.exception

abstract class MeetingServiceException : RuntimeException {

    protected abstract val description: String

    constructor() : super()

    constructor(message: String?) : super(message)

    constructor(message: String?, cause: Throwable?) : super(message, cause)

    constructor(cause: Throwable?) : super(cause)
}
