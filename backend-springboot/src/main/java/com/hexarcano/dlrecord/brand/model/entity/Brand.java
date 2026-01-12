package com.hexarcano.dlrecord.brand.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents the core domain model for a Brand.
 * <p>
 * This class is a "Rich Domain Object" that protects its own invariants (business rules).
 * It is completely independent of any framework-specific logic.
 * </p>
 */
@Getter
@ToString
@EqualsAndHashCode
public class Brand {
    private final String uuid;
    private String name;

    /**
     * Constructs a new Brand, ensuring its initial state is valid.
     * @param uuid The unique identifier.
     * @param name The name of the brand, which will be validated.
     * @throws IllegalArgumentException if the name is invalid.
     */
    public Brand(String uuid, String name) {
        validateName(name);
        this.uuid = uuid;
        this.name = name;
    }

    /**
     * Changes the name of the brand, ensuring the new name is valid.
     * @param newName The new name for the brand.
     * @throws IllegalArgumentException if the new name is invalid.
     */
    public void changeName(String newName) {
        validateName(newName);
        this.name = newName;
    }

    /**
     * Private helper method to enforce business rules on the brand's name.
     * @param name The name to validate.
     * @throws IllegalArgumentException if the validation fails.
     */
    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Brand name cannot be null or empty.");
        }
        // Use \\p{L} to match any Unicode letter character, including accents.
        if (!name.matches("^[\\p{L}\\s]+$")) {
            throw new IllegalArgumentException("Brand name can only contain letters and spaces.");
        }
    }
}
