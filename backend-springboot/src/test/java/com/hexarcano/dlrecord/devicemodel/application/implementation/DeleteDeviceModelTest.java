package com.hexarcano.dlrecord.devicemodel.application.implementation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexarcano.dlrecord.devicemodel.application.port.out.DeviceModelRepositoryPort;

@ExtendWith(MockitoExtension.class)
class DeleteDeviceModelTest {

    @Mock
    private DeviceModelRepositoryPort deviceModelRepository;

    @InjectMocks
    private DeleteDeviceModel deleteDeviceModel;

    @Test
    void shouldDeleteDeviceModelSuccessfully_WhenDeviceModelExists() {
        String uuid = "uuid-123";
        when(deviceModelRepository.deleteById(uuid)).thenReturn(true);

        boolean result = deleteDeviceModel.deleteDeviceModel(uuid);

        assertTrue(result);
        verify(deviceModelRepository, times(1)).deleteById(uuid);
    }

    @Test
    void shouldReturnFalse_WhenDeviceModelDoesNotExist() {
        String uuid = "uuid-123";
        when(deviceModelRepository.deleteById(uuid)).thenReturn(false);

        boolean result = deleteDeviceModel.deleteDeviceModel(uuid);

        assertFalse(result);
        verify(deviceModelRepository, times(1)).deleteById(uuid);
    }
}
