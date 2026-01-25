package com.hexarcano.dlrecord.devicemodel.application.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexarcano.dlrecord.brand.domain.model.Brand;
import com.hexarcano.dlrecord.devicemodel.application.port.out.DeviceModelRepositoryPort;
import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;

@ExtendWith(MockitoExtension.class)
class RetrieveDeviceModelTest {

    @Mock
    private DeviceModelRepositoryPort deviceModelRepository;

    @InjectMocks
    private RetrieveDeviceModel retrieveDeviceModel;

    @Test
    void shouldReturnDeviceModel_WhenExists() {
        // Arrange
        String uuid = "dm-123";
        Brand brand = new Brand("b-1", "Sony");
        DeviceModel expected = new DeviceModel(uuid, "PS5", brand);

        when(deviceModelRepository.findById(uuid)).thenReturn(Optional.of(expected));

        // Act
        Optional<DeviceModel> result = retrieveDeviceModel.findById(uuid);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("PS5", result.get().getName());
    }

    @Test
    void shouldReturnEmpty_WhenDoesNotExist() {
        // Arrange
        String uuid = "dm-404";
        when(deviceModelRepository.findById(uuid)).thenReturn(Optional.empty());

        // Act
        Optional<DeviceModel> result = retrieveDeviceModel.findById(uuid);

        // Assert
        assertTrue(result.isEmpty());
    }
}
