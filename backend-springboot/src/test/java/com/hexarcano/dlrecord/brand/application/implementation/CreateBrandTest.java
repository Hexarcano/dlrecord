package com.hexarcano.dlrecord.brand.application.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexarcano.dlrecord.brand.application.port.in.command.CreateBrandCommand;
import com.hexarcano.dlrecord.brand.application.port.out.BrandRepositoryPort;
import com.hexarcano.dlrecord.brand.domain.model.Brand;

@ExtendWith(MockitoExtension.class)
class CreateBrandTest {

    @Mock
    private BrandRepositoryPort brandRepository;

    @InjectMocks
    private CreateBrand createBrand;

    @Test
    void shouldCreateBrandSuccessfully() {
        CreateBrandCommand command = new CreateBrandCommand("Samsung");

        Brand expectedSavedBrand = new Brand("uuid-123", "Samsung");
        when(brandRepository.save(any(Brand.class))).thenReturn(expectedSavedBrand);
        Brand result = createBrand.createBrand(command);

        assertNotNull(result);
        assertEquals("Samsung", result.getName());
        assertEquals("uuid-123", result.getUuid());

        verify(brandRepository, times(1)).save(any(Brand.class));
    }

    @Test
    void shouldThrowException_WhenNameIsInvalid() {
        CreateBrandCommand command = new CreateBrandCommand("");

        assertThrows(IllegalArgumentException.class, () -> createBrand.createBrand(command));

        verify(brandRepository, never()).save(any(Brand.class));
    }

    @Test
    void shouldThrowException_WhenNameContainsInvalidCharacters() {
        CreateBrandCommand command = new CreateBrandCommand("Samsung123");

        assertThrows(IllegalArgumentException.class, () -> createBrand.createBrand(command));

        verify(brandRepository, never()).save(any(Brand.class));
    }
}
