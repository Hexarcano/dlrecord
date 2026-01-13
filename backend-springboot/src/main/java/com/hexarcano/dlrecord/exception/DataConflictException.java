package com.hexarcano.dlrecord.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a data integrity conflict occurs, such as trying to insert a duplicate record.
 * This maps to an HTTP 409 Conflict status.
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class DataConflictException extends RuntimeException {
    /**
     * Constructs a new DataConflictException with the specified detail message.
     *
     * @param message The detail message.
     */
    public DataConflictException(String message) {
        super(message);
    }
}
