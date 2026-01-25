package com.hexarcano.dlrecord.devicemodel.application.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.hexarcano.dlrecord.devicemodel.application.port.in.command.CreateDeviceModelCommand;
import com.hexarcano.dlrecord.devicemodel.application.port.out.DeviceModelRepositoryPort;
import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;

@ExtendWith(MockitoExtension.class)
class CreateDeviceModelTest {

    @Mock
    private DeviceModelRepositoryPort deviceModelRepository;

    @Mock
    private BrandRepositoryPort brandRepository;

    @InjectMocks
    private CreateDeviceModel createDeviceModel;

    @Test
    void shouldCreateDeviceModelSuccessfully() {
        // Arrange
        String brandId = "brand-123";
        Brand brand = new Brand(brandId, "Sony");
        CreateDeviceModelCommand command = new CreateDeviceModelCommand("PlayStation 5", brandId);
        DeviceModel expectedSaved = new DeviceModel("dm-1", "PlayStation 5", brand);

        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));
        when(deviceModelRepository.existsByName("PlayStation 5")).thenReturn(false);
        when(deviceModelRepository.save(any(DeviceModel.class))).thenReturn(expectedSaved);

        // Act
        DeviceModel result = createDeviceModel.createDeviceModel(command);

        // Assert
        assertNotNull(result);
        assertEquals("PlayStation 5", result.getName());
        assertEquals("Sony", result.getBrand().getName());
        verify(deviceModelRepository, times(1)).save(any(DeviceModel.class));
    }

    @Test
    void shouldThrowException_WhenBrandNotFound() {
        // Arrange
        String brandId = "brand-999";
        CreateDeviceModelCommand command = new CreateDeviceModelCommand("Unknown", brandId);

        when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            createDeviceModel.createDeviceModel(command);
        });

        // Verify no save
        verify(deviceModelRepository, never()).save(any(DeviceModel.class));
    }

    @Test
    void shouldThrowException_WhenNameDuplicated() {
        // Arrange
        String brandId = "brand-123";
        Brand brand = new Brand(brandId, "Sony");
        CreateDeviceModelCommand command = new CreateDeviceModelCommand("ExistingModel", brandId);

        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));
        when(deviceModelRepository.existsByName("ExistingModel")).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            createDeviceModel.createDeviceModel(command);
        });

        verify(deviceModelRepository, never()).save(any(DeviceModel.class));
    }
}
