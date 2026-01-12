package com.hexarcano.dlrecord.devicetype.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents the core domain model for a DeviceType.
 * <p>
 * Rich Domain Object that protects its own business rules.
 * </p>
 */
@Getter
@ToString
@EqualsAndHashCode
public class DeviceType {
    private final String uuid;
    private String name;

    /**
     * Constructs a new DeviceType, ensuring its initial state is valid.
     * 
     * @param uuid The unique identifier.
     * @param name The name of the device type, which will be validated.
     * @throws IllegalArgumentException if the name is invalid.
     */
    public DeviceType(String uuid, String name) {
        validateName(name);
        this.uuid = uuid;
        this.name = name;
    }

    /**
     * Changes the name of the device type, ensuring the new name is valid.
     * 
     * @param newName The new name for the device type.
     * @throws IllegalArgumentException if the new name is invalid.
     */
    public void changeName(String newName) {
        validateName(newName);
        this.name = newName;
    }

    /**
     * Private helper method to enforce business rules on the device type's name.
     * 
     * @param name The name to validate.
     * @throws IllegalArgumentException if the validation fails.
     */
    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("DeviceType name cannot be null or empty.");
        }
        // Use \p{L} to match any Unicode letter character, including accents.
        if (!name.matches("^[\\p{L}\\s]+$")) {
            throw new IllegalArgumentException("DeviceType name can only contain letters and spaces.");
        }
    }
}
