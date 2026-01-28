package com.hexarcano.dlrecord.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hexarcano.dlrecord.brand.domain.exception.BrandAlreadyExistsException;
import com.hexarcano.dlrecord.brand.domain.exception.BrandNotFoundException;
import com.hexarcano.dlrecord.config.exception.UsernameNotFoundException;
import com.hexarcano.dlrecord.device.domain.exception.DeviceInvalidDataException;

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

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }

    /**
     * Handles validation errors specifically for @Valid annotations.
     * Uses standard validation error response format with timestamp.
     *
     * @param ex The MethodArgumentNotValidException from validation failure.
     * @return A ResponseEntity with structured validation error details.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();

        response.put("timestamp", java.time.Instant.now().toString());
        response.put("error", "Bad Request");
        response.put("status", 400);
        response.put("details", ex.getBindingResult().getFieldError().getDefaultMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleBrandNotFoundException(BrandNotFoundException ex) {
        Map<String, String> errorBody = Map.of("error", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }

    @ExceptionHandler(BrandAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleBrandAlreadyExistsException(BrandAlreadyExistsException ex) {
        Map<String, String> errorBody = Map.of("error", ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorBody);
    }

    @ExceptionHandler(DeviceInvalidDataException.class)
    public ResponseEntity<Map<String, String>> handleDeviceInvalidDataException(DeviceInvalidDataException ex) {
        Map<String, String> errorBody = Map.of("error", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
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

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorBody);
    }
}
