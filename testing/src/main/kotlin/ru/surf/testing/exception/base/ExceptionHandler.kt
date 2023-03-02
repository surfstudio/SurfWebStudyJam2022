package ru.surf.testing.exception.base

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.surf.testing.exception.*


@ControllerAdvice
class ExceptionHandler {
    private val logger: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java.name)

    @ExceptionHandler(
            BadAnswerException::class,
            NoSuchCandidateException::class,
            NoSuchEventException::class,
            NoSuchTestException::class,
            TestingPhaseActiveException::class,
            TestingPhaseCompleteException::class,
            TestingPhaseNotActiveException::class,
            TestNotStartedException::class,
            TestStartedException::class,
    )
    fun handleClientExceptions(exception: TestingException): ResponseEntity<ErrorResponse> =
            ResponseEntity<ErrorResponse>(
                    ErrorResponse(exception::class.simpleName, exception.message, exception.getParams()),
                    HttpStatus.BAD_REQUEST)

    @ExceptionHandler(Exception::class)
    fun handleServerExceptions(exception: Exception): ResponseEntity<ErrorResponse> {
        logger.error("An exception during processing request", exception)
        return ResponseEntity<ErrorResponse>(
                ErrorResponse("unknown error", "something went wrong"),
                HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
