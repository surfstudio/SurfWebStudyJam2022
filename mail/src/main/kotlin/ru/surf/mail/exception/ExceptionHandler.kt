package ru.surf.mail.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mail.MailException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler(
    private val logger: Logger = LoggerFactory.getLogger(ExceptionHandler::class.simpleName)
) {

    @ExceptionHandler(MailException::class)
    fun handleMailException(exception: MailException): ResponseEntity<String> {
        logger.error("Error while sending mail", exception)
        return ResponseEntity("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}