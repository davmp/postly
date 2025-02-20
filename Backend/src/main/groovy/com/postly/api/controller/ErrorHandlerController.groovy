package com.postly.api.controller

import com.amazonaws.services.lightsail.model.UnauthenticatedException
import org.apache.coyote.BadRequestException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorHandlerController {
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    ResponseEntity handleNotFound() {
        return ResponseEntity.notFound().build()
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBadRequest(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(ex.getFieldErrors().stream().map(ValidationErrorData::new).toList())
    }

    @ExceptionHandler(UnauthenticatedException.class)
    ResponseEntity handleUnauthorized(UnauthenticatedException ex) {
        return ResponseEntity.status(401).body(ex.getMessage()).build()
    }

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity handleBadRequest(BadRequestException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage())
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity handleBadRequest(DataIntegrityViolationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage())
    }

    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity handleBadRequest(AuthenticationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage())
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity handleInternalServerError(Exception ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage())
    }

    private record ValidationErrorData(String field, String message) {
        ValidationErrorData(FieldError error) {
            this(error.getField(), error.getDefaultMessage())
        }
    }
}
