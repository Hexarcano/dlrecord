package com.hexarcano.dlrecord.brand.application.implementation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexarcano.dlrecord.brand.application.port.out.BrandRepositoryPort;
import com.hexarcano.dlrecord.config.exception.DataConflictException;
import com.hexarcano.dlrecord.devicemodel.application.port.out.DeviceModelRepositoryPort;

@ExtendWith(MockitoExtension.class)
class DeleteBrandTest {

    @Mock
    private BrandRepositoryPort brandRepository;

    @Mock
    private DeviceModelRepositoryPort deviceModelRepository;

    @InjectMocks
    private DeleteBrand deleteBrand;

    @Test
    void shouldDeleteBrandSuccessfully_WhenBrandExistsAndNoAssociatedModels() {
        String uuid = "uuid-123";
        when(deviceModelRepository.countByBrandUuid(uuid)).thenReturn(0L);
        when(brandRepository.deleteById(uuid)).thenReturn(true);

        boolean result = deleteBrand.deleteBrand(uuid);

        assertTrue(result);
        verify(deviceModelRepository, times(1)).countByBrandUuid(uuid);
        verify(brandRepository, times(1)).deleteById(uuid);
    }

    @Test
    void shouldReturnFalse_WhenBrandDoesNotExist() {
        String uuid = "uuid-123";
        when(deviceModelRepository.countByBrandUuid(uuid)).thenReturn(0L);
        when(brandRepository.deleteById(uuid)).thenReturn(false);

        boolean result = deleteBrand.deleteBrand(uuid);

        assertFalse(result);
        verify(brandRepository, times(1)).deleteById(uuid);
    }

    @Test
    void shouldThrowException_WhenBrandHasAssociatedModels() {
        String uuid = "uuid-123";
        when(deviceModelRepository.countByBrandUuid(uuid)).thenReturn(5L);

        assertThrows(DataConflictException.class, () -> deleteBrand.deleteBrand(uuid));

        verify(brandRepository, never()).deleteById(uuid);
    }
}
