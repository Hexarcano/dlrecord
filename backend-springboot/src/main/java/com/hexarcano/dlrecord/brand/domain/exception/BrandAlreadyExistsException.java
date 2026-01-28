package com.hexarcano.dlrecord.brand.domain.exception;

/**
 * Exception thrown when attempting to create a brand that already exists in
 * system.
 * 
 * <p>
 * This is a domain-specific exception that represents a business rule violation
 * when trying to create a brand with a name that already exists in the system.
 * </p>
 */
public class BrandAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new BrandAlreadyExistsException with specified detail message.
     * 
     * @param message The detail message explaining why brand creation failed.
     */
    public BrandAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new BrandAlreadyExistsException with specified detail message
     * and cause.
     * 
     * @param message The detail message explaining why brand creation failed.
     * @param cause   The cause of exception.
     */
    public BrandAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new BrandAlreadyExistsException for a specific brand name.
     * 
     * @param name The name of brand that already exists.
     * @return A new BrandAlreadyExistsException with a formatted message.
     */
    public static BrandAlreadyExistsException ofName(String name) {
        return new BrandAlreadyExistsException("Brand already exists");
    }
}