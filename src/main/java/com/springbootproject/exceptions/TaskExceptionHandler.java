package com.springbootproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class TaskExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<TaskExceptionResponse> taskNotFoundException(TaskNotFoundException ex) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        TaskExceptionResponse response = new TaskExceptionResponse(
                ex.getMessage(),
                status,
                ZonedDateTime.now()
        );
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(TaskAlreadyExistsException.class)
    public ResponseEntity<TaskExceptionResponse> taskAlreadyExistsHandler(TaskAlreadyExistsException ex) {

        HttpStatus status = HttpStatus.CONFLICT;

        TaskExceptionResponse response = new TaskExceptionResponse(
                ex.getMessage(),
                status,
                ZonedDateTime.now()
        );

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<TaskExceptionResponse> badRequestException(BadRequestException ex) {

        TaskExceptionResponse response = new TaskExceptionResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return ResponseEntity.badRequest().body(response);
    }

}
