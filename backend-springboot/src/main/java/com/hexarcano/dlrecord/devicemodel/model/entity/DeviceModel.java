package com.hexarcano.dlrecord.devicemodel.model.entity;

import com.hexarcano.dlrecord.brand.model.entity.Brand;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class DeviceModel {
    private final String uuid;
    private String name;
    private Brand brand;

    public DeviceModel(String uuid, String name, Brand brand) {
        validateName(name);
        validateBrand(brand);
        this.uuid = uuid;
        this.name = name;
        this.brand = brand;
    }

    public void changeName(String newName) {
        validateName(newName);
        this.name = newName;
    }

    public void changeBrand(Brand newBrand) {
        validateBrand(newBrand);
        this.brand = newBrand;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("DeviceModel name cannot be null or empty.");
        }
        if (!name.matches("^[\\p{L}0-9\\s.-]+$")) {
            throw new IllegalArgumentException(
                    "DeviceModel name can only contain letters, numbers, spaces, dots, and hyphens.");
        }
    }

    private void validateBrand(Brand brand) {
        if (brand == null || brand.getUuid() == null) {
            throw new IllegalArgumentException("Brand cannot be null.");
        }
    }
}
