package com.hexarcano.dlrecord.brand.domain.exception;

/**
 * Exception thrown when a brand is not found in the system.
 * 
 * <p>
 * This is a domain-specific exception that represents a business rule violation
 * when attempting to retrieve, update, or delete a brand that does not exist.
 * </p>
 */
public class BrandNotFoundException extends RuntimeException {
    
    /**
     * Constructs a new BrandNotFoundException with the specified detail message.
     * 
     * @param message The detail message explaining why the brand was not found.
     */
    public BrandNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new BrandNotFoundException with the specified detail message and cause.
     * 
     * @param message The detail message explaining why the brand was not found.
     * @param cause The cause of the exception.
     */
    public BrandNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Constructs a new BrandNotFoundException for a specific brand UUID.
     * 
     * @param uuid The UUID of the brand that was not found.
     * @return A new BrandNotFoundException with a formatted message.
     */
    public static BrandNotFoundException ofUuid(String uuid) {
        return new BrandNotFoundException("Brand not found");
    }
}