package ru.surf.core.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.surf.core.dto.ErrorDto
import ru.surf.core.exception.defence.DefenceCreationFailedException
import ru.surf.core.exception.event.EventNotFoundByIdException
import ru.surf.core.exception.event.EventReportNotFoundException
import java.time.LocalDateTime


@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(EventNotFoundByIdException::class)
    fun handleEventNotFoundByIdException(exception: EventNotFoundByIdException): ResponseEntity<ErrorDto> {
        val errorDto = ErrorDto(exception.exceptionType.toString(), exception.message, LocalDateTime.now())
        return ResponseEntity(errorDto, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(EventReportNotFoundException::class)
    fun handleEventReportNotFoundException(exception: EventReportNotFoundException): ResponseEntity<ErrorDto> {
        val errorDto = ErrorDto(exception.exceptionType.toString(), exception.message, LocalDateTime.now())
        return ResponseEntity(errorDto, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(DefenceCreationFailedException::class)
    fun handleDefenceCreationFailedException(exception: DefenceCreationFailedException): ResponseEntity<ErrorDto> {
        val errorDto = ErrorDto(exception.exceptionType.toString(), exception.message, LocalDateTime.now())
        return ResponseEntity(errorDto, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException::class)
    fun handleObjectOptimisticLockingFailureException(
        exception: ObjectOptimisticLockingFailureException,
    ): ResponseEntity<ErrorDto> {
        val message = "CONCURRENT UPDATE FAILED"
        val errorDto = ErrorDto(
            exception.toString(),
            message,
            LocalDateTime.now()
        )
        return ResponseEntity(errorDto, HttpStatus.CONFLICT)
    }

}