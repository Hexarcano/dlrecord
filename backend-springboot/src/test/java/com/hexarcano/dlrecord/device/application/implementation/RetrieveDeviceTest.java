package com.hexarcano.dlrecord.device.application.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.hexarcano.dlrecord.device.application.port.out.DeviceRepositoryPort;
import com.hexarcano.dlrecord.device.domain.model.Device;

@ExtendWith(MockitoExtension.class)
class RetrieveDeviceTest {

    @Mock
    private DeviceRepositoryPort deviceRepository;

    @InjectMocks
    private RetrieveDevice retrieveDevice;

    @Test
    void shouldFindDeviceById_WhenExists() {
        Device device = mock(Device.class);
        when(deviceRepository.findById("uuid-1")).thenReturn(Optional.of(device));

        Optional<Device> result = retrieveDevice.findById("uuid-1");

        assertTrue(result.isPresent());
        assertEquals(device, result.get());
    }

    @Test
    void shouldReturnEmpty_WhenDeviceByIdNotFound() {
        when(deviceRepository.findById("uuid-1")).thenReturn(Optional.empty());

        Optional<Device> result = retrieveDevice.findById("uuid-1");

        assertFalse(result.isPresent());
    }

    @Test
    void shouldFindAllDevices() {
        Pageable pageable = mock(Pageable.class);
        Page<Device> page = new PageImpl<>(Collections.singletonList(mock(Device.class)));
        when(deviceRepository.findAll(pageable)).thenReturn(page);

        Page<Device> result = retrieveDevice.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        verify(deviceRepository).findAll(pageable);
    }
}
