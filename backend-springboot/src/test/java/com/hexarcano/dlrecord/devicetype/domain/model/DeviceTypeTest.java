package com.hexarcano.dlrecord.devicetype.domain.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DeviceTypeTest {

    @ParameterizedTest
    @CsvSource({
            "Smartphone, Smartphone",
            "Smart Phone, Smart Phone",
            "Cámara, Cámara",
            "A, A"
    })
    void shouldCreateDeviceType_WhenNameIsValid(String name, String expectedName) {
        assertDoesNotThrow(() -> new DeviceType("uuid-123", name));

        DeviceType deviceType = new DeviceType("uuid-123", name);
        assertEquals("uuid-123", deviceType.getUuid());
        assertEquals(expectedName, deviceType.getName());
    }

    // Constructor validation tests - Invalid cases
    @Test
    void shouldThrowException_WhenNameIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DeviceType("uuid-123", null);
        });

        assertEquals("DeviceType name cannot be null or empty.", exception.getMessage());
    }

    @Test
    void shouldThrowException_WhenNameIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DeviceType("uuid-123", "");
        });

        assertEquals("DeviceType name cannot be null or empty.", exception.getMessage());
    }

    @Test
    void shouldThrowException_WhenNameIsWhitespaceOnly() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DeviceType("uuid-123", "   ");
        });

        assertEquals("DeviceType name cannot be null or empty.", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "Phone123",
            "Phone_Smart",
            "Phone-Smart",
            "Phone@Smart",
            "Phone.Smart"
    })
    void shouldThrowException_WhenNameContainsInvalidCharacters(String invalidName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DeviceType("uuid-123", invalidName);
        });

        assertEquals("DeviceType name can only contain letters and spaces.", exception.getMessage());
    }

    // Business method tests - changeName
    @ParameterizedTest
    @CsvSource({
            "Tablet, Tablet",
            "Smart Phone, Smart Phone",
            "Cámara, Cámara"
    })
    void shouldChangeName_WhenNewNameIsValid(String newName, String expectedName) {
        DeviceType deviceType = new DeviceType("uuid-123", "Smartphone");

        deviceType.changeName(newName);

        assertEquals(expectedName, deviceType.getName());
    }

    @ParameterizedTest
    @CsvSource({
            "Invalid123, DeviceType name can only contain letters and spaces.",
            "Phone_Smart, DeviceType name can only contain letters and spaces."
    })
    void shouldNotChangeName_WhenNewNameIsInvalid(String invalidName, String expectedMessage) {
        DeviceType deviceType = new DeviceType("uuid-123", "Smartphone");
        String originalName = deviceType.getName();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deviceType.changeName(invalidName);
        });

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(originalName, deviceType.getName());
    }

    @ParameterizedTest
    @CsvSource({
            "null, DeviceType name cannot be null or empty.",
            "'', DeviceType name cannot be null or empty.",
            "'   ', DeviceType name cannot be null or empty."
    })
    void shouldNotChangeName_WhenNewNameIsEmpty(String newName, String expectedMessage) {
        DeviceType deviceType = new DeviceType("uuid-123", "Smartphone");
        String originalName = deviceType.getName();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deviceType.changeName("null".equals(newName) ? null : newName);
        });

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(originalName, deviceType.getName());
    }
}