package com.hexarcano.dlrecord.devicetype.application.implementation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexarcano.dlrecord.devicetype.application.port.out.DeviceTypeRepositoryPort;

@ExtendWith(MockitoExtension.class)
class DeleteDeviceTypeTest {

    @Mock
    private DeviceTypeRepositoryPort deviceTypeRepository;

    @InjectMocks
    private DeleteDeviceType deleteDeviceType;

    @Test
    void deleteDeviceType_ShouldReturnTrue_WhenSuccessful() {
        // Arrange
        String uuid = "uuid-123";
        when(deviceTypeRepository.deleteById(uuid)).thenReturn(true);

        // Act
        boolean result = deleteDeviceType.deleteDeviceType(uuid);

        // Assert
        assertTrue(result);
    }

    @Test
    void deleteDeviceType_ShouldReturnFalse_WhenNotFound() {
        // Arrange
        String uuid = "uuid-123";
        when(deviceTypeRepository.deleteById(uuid)).thenReturn(false);

        // Act
        boolean result = deleteDeviceType.deleteDeviceType(uuid);

        // Assert
        assertFalse(result);
    }
}
