package com.hexarcano.dlrecord.devicetype.application.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexarcano.dlrecord.devicetype.application.port.in.command.UpdateDeviceTypeCommand;
import com.hexarcano.dlrecord.devicetype.application.port.out.DeviceTypeRepositoryPort;
import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;

@ExtendWith(MockitoExtension.class)
class UpdateDeviceTypeTest {

    @Mock
    private DeviceTypeRepositoryPort deviceTypeRepository;

    @InjectMocks
    private UpdateDeviceType updateDeviceType;

    @Test
    void updateDeviceType_ShouldReturnUpdatedDeviceType_WhenExists() {
        // Arrange
        String uuid = "uuid-123";
        UpdateDeviceTypeCommand command = new UpdateDeviceTypeCommand("Smartphone Updated");
        DeviceType existingDt = new DeviceType(uuid, "Smartphone");
        Optional<DeviceType> updatedDt = Optional.ofNullable(new DeviceType(uuid, "Smartphone Updated"));

        when(deviceTypeRepository.findById(uuid)).thenReturn(Optional.of(existingDt));
        when(deviceTypeRepository.save(any(DeviceType.class))).thenReturn(updatedDt.get());

        // Act
        Optional<DeviceType> result = updateDeviceType.updateDeviceType(uuid, command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Smartphone Updated", result.get().getName());
        verify(deviceTypeRepository).save(any(DeviceType.class));
    }

    @Test
    void updateDeviceType_ShouldReturnEmpty_WhenNotExists() {
        // Arrange
        String uuid = "uuid-123";
        UpdateDeviceTypeCommand command = new UpdateDeviceTypeCommand("Smartphone Updated");

        when(deviceTypeRepository.findById(uuid)).thenReturn(Optional.empty());

        // Act
        Optional<DeviceType> result = updateDeviceType.updateDeviceType(uuid, command);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void updateDeviceType_ShouldThrowException_WhenNameIsInvalid() {
        // Arrange
        String uuid = "uuid-123";
        UpdateDeviceTypeCommand command = new UpdateDeviceTypeCommand("Smart_phone123");
        DeviceType existingDt = new DeviceType(uuid, "Smartphone");

        when(deviceTypeRepository.findById(uuid)).thenReturn(Optional.of(existingDt));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            updateDeviceType.updateDeviceType(uuid, command);
        });

        assertEquals("DeviceType name can only contain letters and spaces.", exception.getMessage());
        verify(deviceTypeRepository, never()).save(any(DeviceType.class));
    }
}
