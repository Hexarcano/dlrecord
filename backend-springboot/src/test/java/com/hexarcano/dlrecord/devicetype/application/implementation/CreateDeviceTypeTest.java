package com.hexarcano.dlrecord.devicetype.application.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexarcano.dlrecord.devicetype.application.port.in.command.CreateDeviceTypeCommand;
import com.hexarcano.dlrecord.devicetype.application.port.out.DeviceTypeRepositoryPort;
import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;

@ExtendWith(MockitoExtension.class)
class CreateDeviceTypeTest {

    @Mock
    private DeviceTypeRepositoryPort deviceTypeRepository;

    @InjectMocks
    private CreateDeviceType createDeviceType;

    @Test
    void createDeviceType_ShouldReturnCreatedDeviceType() {
        // Arrange
        CreateDeviceTypeCommand command = new CreateDeviceTypeCommand("Smartphone");
        DeviceType savedDeviceType = new DeviceType("uuid-123", "Smartphone");

        when(deviceTypeRepository.save(any(DeviceType.class))).thenReturn(savedDeviceType);

        // Act
        DeviceType result = createDeviceType.createDeviceType(command);

        // Assert
        assertNotNull(result);
        assertEquals("uuid-123", result.getUuid());
        assertEquals("Smartphone", result.getName());
        verify(deviceTypeRepository).save(any(DeviceType.class));
    }

    @Test
    void createDeviceType_ShouldThrowException_WhenNameIsInvalid() {
        // Arrange
        CreateDeviceTypeCommand command = new CreateDeviceTypeCommand("Smart_phone123");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createDeviceType.createDeviceType(command);
        });

        assertEquals("DeviceType name can only contain letters and spaces.", exception.getMessage());
        verify(deviceTypeRepository, never()).save(any(DeviceType.class));
    }
}
