package com.hexarcano.dlrecord.brand.domain.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BrandTest {

    @ParameterizedTest
    @CsvSource({
            "Samsung, Samsung",
            "Sony, Sony",
            "Apple, Apple",
            "Cámara, Cámara",
            "LG, LG",
            "A, A"
    })
    void shouldCreateBrand_WhenNameIsValid(String name, String expectedName) {
        assertDoesNotThrow(() -> new Brand("uuid-123", name));

        Brand brand = new Brand("uuid-123", name);
        assertEquals("uuid-123", brand.getUuid());
        assertEquals(expectedName, brand.getName());
    }

    @ParameterizedTest
    @CsvSource({
            "Samsung123",
            "Samsung_Smart",
            "Samsung@Smart",
            "Samsung.Smart",
            "Samsung#Smart",
            "Samsung Smart!"
    })
    void shouldThrowException_WhenNameContainsInvalidCharacters(String invalidName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Brand("uuid-123", invalidName);
        });

        assertEquals("Brand name can only contain letters and spaces.", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "Tablet, Tablet",
            "Smart Phone, Smart Phone",
            "Cámara, Cámara",
            "Xiaomi, Xiaomi"
    })
    void shouldChangeName_WhenNewNameIsValid(String newName, String expectedName) {
        Brand brand = new Brand("uuid-123", "Samsung");

        brand.changeName(newName);

        assertEquals(expectedName, brand.getName());
    }

    @ParameterizedTest
    @CsvSource({
            "Tablet123, Brand name can only contain letters and spaces.",
            "Tablet_Smart, Brand name can only contain letters and spaces.",
            "Tablet@Smart, Brand name can only contain letters and spaces.",
            "Tablet.Smart, Brand name can only contain letters and spaces."
    })
    void shouldNotChangeName_WhenNewNameIsInvalid(String newName, String expectedMessage) {
        Brand brand = new Brand("uuid-123", "Samsung");
        String originalName = brand.getName();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            brand.changeName(newName);
        });

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(originalName, brand.getName());
    }

    @Test
    void shouldCreateBrand_WhenNameContainsSpaces() {
        assertDoesNotThrow(() -> new Brand("uuid-123", "Sony Ericsson"));

        Brand brand = new Brand("uuid-123", "Sony Ericsson");
        assertEquals("Sony Ericsson", brand.getName());
    }

    @Test
    void shouldCreateBrand_WhenNameContainsUnicode() {
        assertDoesNotThrow(() -> new Brand("uuid-123", "Huawei"));

        Brand brand = new Brand("uuid-123", "Huawei");
        assertEquals("Huawei", brand.getName());
    }

    @Test
    void shouldChangeName_WhenNewNameContainsSpaces() {
        Brand brand = new Brand("uuid-123", "OnePlus");

        brand.changeName("One Plus");

        assertEquals("One Plus", brand.getName());
    }

    @Test
    void shouldNotChangeName_WhenValidationFails() {
        Brand brand = new Brand("uuid-123", "Samsung");
        String originalName = brand.getName();

        assertThrows(IllegalArgumentException.class, () -> {
            brand.changeName("Invalid123");
        });

        assertEquals(originalName, brand.getName());
    }
}