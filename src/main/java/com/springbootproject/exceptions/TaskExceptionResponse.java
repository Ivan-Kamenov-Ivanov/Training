package com.springbootproject.exceptions;

import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

public record TaskExceptionResponse(String message, HttpStatus httpStatus, ZonedDateTime timeStamp) {

}
