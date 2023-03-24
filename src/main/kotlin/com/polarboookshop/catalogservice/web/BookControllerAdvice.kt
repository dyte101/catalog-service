package com.polarboookshop.catalogservice.web

import com.polarboookshop.catalogservice.domain.BookAlreadyExistsException
import com.polarboookshop.catalogservice.domain.BookNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class BookControllerAdvice {

    @ExceptionHandler(BookNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun bookNotFoundHandler(ex: BookNotFoundException): String? {
        return ex.message
    }

    @ExceptionHandler(BookAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun bookAlreadyExistsHandler(ex: BookAlreadyExistsException): String? {
        return ex.message
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): Map<String, String> {
        var errors = HashMap<String, String>()
        ex.bindingResult.allErrors.forEach { error ->
            val fieldName: String = (error as FieldError).field
            val errorMessage: String? = error.defaultMessage
            errors[fieldName] = errorMessage ?: "Validation Error"
        }
        return errors
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequest(ex: HttpMessageNotReadableException): String? {
        var message = ex.message!!.split(":")
        return ("Error type: ${message[0]} \nReason: ${message[1]}")
    }
}