package com.hexarcano.dlrecord.devicetype.application.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexarcano.dlrecord.devicetype.application.port.out.DeviceTypeRepositoryPort;
import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;

@ExtendWith(MockitoExtension.class)
class RetrieveDeviceTypeTest {

    @Mock
    private DeviceTypeRepositoryPort deviceTypeRepository;

    @InjectMocks
    private RetrieveDeviceType retrieveDeviceType;

    @Test
    void shouldReturnDeviceType_WhenExists() {
        // Arrange
        String uuid = "uuid-123";
        DeviceType deviceType = new DeviceType(uuid, "Smartphone");

        when(deviceTypeRepository.findById(uuid)).thenReturn(Optional.of(deviceType));

        // Act
        Optional<DeviceType> result = retrieveDeviceType.findById(uuid);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Smartphone", result.get().getName());
    }

    @Test
    void shouldReturnEmpty_WhenNotExists() {
        // Arrange
        String uuid = "uuid-123";

        when(deviceTypeRepository.findById(uuid)).thenReturn(Optional.empty());

        // Act
        Optional<DeviceType> result = retrieveDeviceType.findById(uuid);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnListOfDeviceTypes() {
        // Arrange
        DeviceType dt1 = new DeviceType("1", "Smartphone");
        DeviceType dt2 = new DeviceType("2", "Tablet");

        when(deviceTypeRepository.findAll()).thenReturn(Arrays.asList(dt1, dt2));

        // Act
        List<DeviceType> result = retrieveDeviceType.findAll();

        // Assert
        assertEquals(2, result.size());
    }
}
