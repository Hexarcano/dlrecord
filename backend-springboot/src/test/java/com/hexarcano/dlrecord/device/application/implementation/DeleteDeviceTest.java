package com.hexarcano.dlrecord.device.application.implementation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexarcano.dlrecord.device.application.port.out.DeviceRepositoryPort;

@ExtendWith(MockitoExtension.class)
class DeleteDeviceTest {

    @Mock
    private DeviceRepositoryPort deviceRepository;

    @InjectMocks
    private DeleteDevice deleteDevice;

    @Test
    void shouldReturnTrue_WhenDeviceDeletedSuccessfully() {
        when(deviceRepository.deleteById("uuid-1")).thenReturn(true);

        boolean result = deleteDevice.deleteDevice("uuid-1");

        assertTrue(result);
    }

    @Test
    void shouldReturnFalse_WhenDeviceNotFoundToDelete() {
        when(deviceRepository.deleteById("uuid-1")).thenReturn(false);

        boolean result = deleteDevice.deleteDevice("uuid-1");

        assertFalse(result);
    }
}
