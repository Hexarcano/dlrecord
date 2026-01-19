package com.hexarcano.dlrecord.devicemodel.domain.model;

import com.hexarcano.dlrecord.brand.domain.model.Brand;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents the core domain model for a Device Model.
 * 
 * <p>
 * Rich Domain Object that protects its own business rules.
 * </p>
 */
@Getter
@ToString
@EqualsAndHashCode
public class DeviceModel {
    private final String uuid;
    private String name;
    private Brand brand;

    /**
     * Constructs a new DeviceModel, ensuring its initial state is valid.
     * 
     * @param uuid  The unique identifier.
     * @param name  The name of the device model, which will be validated.
     * @param brand The associated brand, which must not be null.
     * @throws IllegalArgumentException if the name is invalid or the brand is null.
     */
    public DeviceModel(String uuid, String name, Brand brand) {
        validateName(name);
        validateBrand(brand);
        this.uuid = uuid;
        this.name = name;
        this.brand = brand;
    }

    /**
     * Changes the name of the device model, ensuring the new name is valid.
     * 
     * @param newName The new name for the device model.
     * @throws IllegalArgumentException if the new name is invalid.
     */
    public void changeName(String newName) {
        validateName(newName);
        this.name = newName;
    }

    /**
     * Changes the associated brand of the device model.
     * 
     * @param newBrand The new brand for the device model.
     * @throws IllegalArgumentException if the new brand is null or invalid.
     */
    public void changeBrand(Brand newBrand) {
        validateBrand(newBrand);
        this.brand = newBrand;
    }

    /**
     * Private helper method to enforce business rules on the device model's name.
     * 
     * @param name The name to validate.
     * @throws IllegalArgumentException if the validation fails.
     */
    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("DeviceModel name cannot be null or empty.");
        }
        if (!name.matches("^[\\p{L}0-9\\s.-]+$")) {
            throw new IllegalArgumentException(
                    "DeviceModel name can only contain letters, numbers, spaces, dots, and hyphens.");
        }
    }

    /**
     * Private helper method to ensure the brand is valid.
     * 
     * @param brand The brand to validate.
     * @throws IllegalArgumentException if the brand is null.
     */
    private void validateBrand(Brand brand) {
        if (brand == null || brand.getUuid() == null) {
            throw new IllegalArgumentException("Brand cannot be null.");
        }
    }
}
