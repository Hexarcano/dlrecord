package com.hexarcano.dlrecord.device.application.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexarcano.dlrecord.brand.domain.model.Brand;
import com.hexarcano.dlrecord.device.application.port.in.command.CreateDeviceCommand;
import com.hexarcano.dlrecord.device.application.port.out.DeviceRepositoryPort;
import com.hexarcano.dlrecord.device.domain.exception.DeviceInvalidDataException;
import com.hexarcano.dlrecord.device.domain.model.Device;
import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;
import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;

@ExtendWith(MockitoExtension.class)
class CreateDeviceTest {

    @Mock
    private DeviceRepositoryPort deviceRepository;

    @InjectMocks
    private CreateDevice createDevice;

    @Test
    void shouldCreateDeviceSuccessfully() {
        CreateDeviceCommand command = new CreateDeviceCommand(
                "My Device", "192.168.1.1/24", "fe80::1", null, "brand-1", "model-1", "type-1", "inv-1");

        Brand brand = mock(Brand.class);
        DeviceModel model = mock(DeviceModel.class);
        DeviceType type = mock(DeviceType.class);

        when(deviceRepository.findBrandById("brand-1")).thenReturn(Optional.of(brand));
        when(deviceRepository.findDeviceModelById("model-1")).thenReturn(Optional.of(model));
        when(deviceRepository.findDeviceTypeById("type-1")).thenReturn(Optional.of(type));

        Device expectedDevice = new Device("uuid-1", "My Device", "192.168.1.1/24", "fe80::1", null, brand, model, type,
                "inv-1");
        when(deviceRepository.save(any(Device.class))).thenReturn(expectedDevice);

        Device result = createDevice.createDevice(command);

        assertNotNull(result);
        assertEquals("uuid-1", result.getUuid());
        assertEquals("My Device", result.getDeviceName());

        verify(deviceRepository).save(any(Device.class));
    }

    @Test
    void shouldThrowException_WhenBrandNotFound() {
        CreateDeviceCommand command = new CreateDeviceCommand(
                "My Device", "192.168.1.1/24", null, null, "brand-X", "model-1", "type-1", "inv-1");

        when(deviceRepository.findBrandById("brand-X")).thenReturn(Optional.empty());

        DeviceInvalidDataException exception = assertThrows(DeviceInvalidDataException.class,
                () -> createDevice.createDevice(command));

        assertEquals("Brand not found", exception.getMessage());
        verify(deviceRepository, never()).save(any(Device.class));
    }

    @Test
    void shouldThrowException_WhenDeviceModelNotFound() {
        CreateDeviceCommand command = new CreateDeviceCommand(
                "My Device", "192.168.1.1/24", null, null, "brand-1", "model-X", "type-1", "inv-1");

        when(deviceRepository.findBrandById("brand-1")).thenReturn(Optional.of(mock(Brand.class)));
        when(deviceRepository.findDeviceModelById("model-X")).thenReturn(Optional.empty());

        DeviceInvalidDataException exception = assertThrows(DeviceInvalidDataException.class,
                () -> createDevice.createDevice(command));

        assertEquals("Device Model not found", exception.getMessage());
        verify(deviceRepository, never()).save(any(Device.class));
    }
}
