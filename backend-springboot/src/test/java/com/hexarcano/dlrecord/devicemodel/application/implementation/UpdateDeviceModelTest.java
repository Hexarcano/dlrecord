package com.hexarcano.dlrecord.devicemodel.application.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexarcano.dlrecord.brand.application.port.out.BrandRepositoryPort;
import com.hexarcano.dlrecord.brand.domain.model.Brand;
import com.hexarcano.dlrecord.devicemodel.application.port.in.command.UpdateDeviceModelCommand;
import com.hexarcano.dlrecord.devicemodel.application.port.out.DeviceModelRepositoryPort;
import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;

@ExtendWith(MockitoExtension.class)
class UpdateDeviceModelTest {

    @Mock
    private DeviceModelRepositoryPort deviceModelRepository;

    @Mock
    private BrandRepositoryPort brandRepository;

    @InjectMocks
    private UpdateDeviceModel updateDeviceModel;

    @Test
    void shouldUpdateName_WhenChanged() {
        // Arrange
        String uuid = "dm-1";
        Brand brand = new Brand("b-1", "Sony");
        DeviceModel existing = new DeviceModel(uuid, "PS4", brand);

        UpdateDeviceModelCommand command = new UpdateDeviceModelCommand("PS5", "b-1");

        when(deviceModelRepository.findById(uuid)).thenReturn(Optional.of(existing));
        when(deviceModelRepository.save(any(DeviceModel.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Optional<DeviceModel> result = updateDeviceModel.updateDeviceModel(uuid, command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("PS5", result.get().getName());
        verify(deviceModelRepository, times(1)).save(any(DeviceModel.class));
    }

    @Test
    void shouldUpdateBrand_WhenChanged() {
        // Arrange
        String uuid = "dm-1";
        Brand oldBrand = new Brand("b-1", "Sony");
        Brand newBrand = new Brand("b-2", "Sony Entertainment");
        DeviceModel existing = new DeviceModel(uuid, "PS5", oldBrand);

        UpdateDeviceModelCommand command = new UpdateDeviceModelCommand("PS5", "b-2"); // Name same, Brand diff

        when(deviceModelRepository.findById(uuid)).thenReturn(Optional.of(existing));
        when(brandRepository.findById("b-2")).thenReturn(Optional.of(newBrand));
        when(deviceModelRepository.save(any(DeviceModel.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Optional<DeviceModel> result = updateDeviceModel.updateDeviceModel(uuid, command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("b-2", result.get().getBrand().getUuid());
        verify(deviceModelRepository, times(1)).save(any(DeviceModel.class));
    }

    @Test
    void shouldNotCallSave_WhenNoChanges() {
        // Arrange
        String uuid = "dm-1";
        Brand brand = new Brand("b-1", "Sony");
        DeviceModel existing = new DeviceModel(uuid, "PS5", brand);

        UpdateDeviceModelCommand command = new UpdateDeviceModelCommand("PS5", "b-1"); // All same

        when(deviceModelRepository.findById(uuid)).thenReturn(Optional.of(existing));

        // Act
        updateDeviceModel.updateDeviceModel(uuid, command);

        // Assert
        verify(deviceModelRepository, never()).save(any(DeviceModel.class));
    }

    @Test
    void shouldThrowException_WhenNewBrandNotFound() {
        // Arrange
        String uuid = "dm-1";
        Brand brand = new Brand("b-1", "Sony");
        DeviceModel existing = new DeviceModel(uuid, "PS5", brand);

        UpdateDeviceModelCommand command = new UpdateDeviceModelCommand("PS5", "b-999");

        when(deviceModelRepository.findById(uuid)).thenReturn(Optional.of(existing));
        when(brandRepository.findById("b-999")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            updateDeviceModel.updateDeviceModel(uuid, command);
        });

        verify(deviceModelRepository, never()).save(any(DeviceModel.class));
    }
}
