package com.springbootproject.exceptions;


import org.springframework.dao.DataAccessException;

public class TaskAlreadyExistsException extends DataAccessException {

    public TaskAlreadyExistsException(String msg) {
        super(msg);
    }

    public TaskAlreadyExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
