package com.hexarcano.dlrecord.device.application.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.hexarcano.dlrecord.device.application.port.in.command.UpdateDeviceCommand;
import com.hexarcano.dlrecord.device.application.port.out.DeviceRepositoryPort;
import com.hexarcano.dlrecord.device.domain.exception.DeviceInvalidDataException;
import com.hexarcano.dlrecord.device.domain.model.Device;
import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;
import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;

@ExtendWith(MockitoExtension.class)
class UpdateDeviceTest {

    @Mock
    private DeviceRepositoryPort deviceRepository;

    @InjectMocks
    private UpdateDevice updateDevice;

    @Test
    void shouldUpdateDeviceSuccessfully() {
        UpdateDeviceCommand command = new UpdateDeviceCommand(
                "uuid-1", "Updated Device", "192.168.1.2/24", null, null,
                "brand-1", "model-1", "type-1", "inv-updated");

        Device existingDevice = mock(Device.class);
        Brand brand = mock(Brand.class);
        DeviceModel model = mock(DeviceModel.class);
        DeviceType type = mock(DeviceType.class);

        when(existingDevice.getBrand()).thenReturn(brand);
        when(existingDevice.getDeviceModel()).thenReturn(model);
        when(existingDevice.getDeviceType()).thenReturn(type);
        when(existingDevice.getBrand().getUuid()).thenReturn("brand-original");
        when(existingDevice.getDeviceModel().getUuid()).thenReturn("model-original");
        when(existingDevice.getDeviceType().getUuid()).thenReturn("type-original");

        when(deviceRepository.findById("uuid-1")).thenReturn(Optional.of(existingDevice));
        when(deviceRepository.findBrandById("brand-1")).thenReturn(Optional.of(brand));
        when(deviceRepository.findDeviceModelById("model-1")).thenReturn(Optional.of(model));
        when(deviceRepository.findDeviceTypeById("type-1")).thenReturn(Optional.of(type));
        when(deviceRepository.save(existingDevice)).thenReturn(existingDevice);

        Device result = updateDevice.updateDevice(command);

        assertEquals(existingDevice, result);
        verify(existingDevice).changeDeviceName("Updated Device");
        verify(existingDevice).changeIpv4("192.168.1.2/24");
        verify(existingDevice).changeBrand(brand);
        verify(existingDevice).changeDeviceModel(model);
        verify(existingDevice).changeDeviceType(type);
        verify(existingDevice).changeInventoryName("inv-updated");
        verify(deviceRepository).save(existingDevice);
    }

    @Test
    void shouldThrowException_WhenDeviceNotFound() {
        UpdateDeviceCommand command = new UpdateDeviceCommand(
                "uuid-1", "Updated", null, null, null, null, null, null, null);

        when(deviceRepository.findById("uuid-1")).thenReturn(Optional.empty());

        assertThrows(DeviceInvalidDataException.class, () -> updateDevice.updateDevice(command));
        verify(deviceRepository, never()).save(any());
    }
}
