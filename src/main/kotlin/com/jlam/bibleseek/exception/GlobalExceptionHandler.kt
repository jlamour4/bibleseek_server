package com.jlam.bibleseek.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.resource.NoResourceFoundException

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFoundException(ex: NoResourceFoundException?): ResponseEntity<*> {
        val errorDetails: MutableMap<String, String> = HashMap()
        errorDetails["status"] = "404"
        errorDetails["error"] = "Not Found"
        errorDetails["message"] = "The requested resource could not be found."
        return ResponseEntity<Map<String, String>>(errorDetails, HttpStatus.NOT_FOUND)
    }
}