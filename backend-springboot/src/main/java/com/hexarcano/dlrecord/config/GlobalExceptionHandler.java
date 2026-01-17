package com.hexarcano.dlrecord.config;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hexarcano.dlrecord.exception.UsernameNotFoundException;

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

    /**
     * Handles authentication exceptions (BadCredentialsException and
     * UsernameNotFoundException).
     * Returns a generic error message to prevent user enumeration.
     *
     * @param ex The captured exception (either BadCredentials or UsernameNotFound).
     * @return A ResponseEntity with HTTP status 401 (Unauthorized) and a generic
     *         error message.
     */
    @ExceptionHandler({ BadCredentialsException.class, UsernameNotFoundException.class })
    public ResponseEntity<Map<String, String>> handleAuthenticationException(RuntimeException ex) {
        Map<String, String> errorBody = Map.of("error", "Bad credentials");
        return new ResponseEntity<>(errorBody, HttpStatus.UNAUTHORIZED);
    }
}
