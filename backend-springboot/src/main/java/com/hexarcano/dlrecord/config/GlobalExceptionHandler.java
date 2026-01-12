package com.hexarcano.dlrecord.config;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the application's controllers.
 * 
 * <p>
 * This class captures specific exceptions and translates them into appropriate
 * HTTP responses.
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles IllegalArgumentException, thrown by domain models when a business
     * rule is violated.
     *
     * @param ex The captured IllegalArgumentException.
     * @return A ResponseEntity with HTTP status 400 (Bad Request) and a
     *         JSON body containing the error message.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorBody = Map.of("error", ex.getMessage());
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }
}
