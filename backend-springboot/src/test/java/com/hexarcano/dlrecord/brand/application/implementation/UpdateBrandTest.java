package com.hexarcano.dlrecord.brand.application.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexarcano.dlrecord.brand.application.port.in.command.UpdateBrandCommand;
import com.hexarcano.dlrecord.brand.application.port.out.BrandRepositoryPort;
import com.hexarcano.dlrecord.brand.domain.model.Brand;

@ExtendWith(MockitoExtension.class)
class UpdateBrandTest {

    @Mock
    private BrandRepositoryPort brandRepository;

    @InjectMocks
    private UpdateBrand updateBrand;

    @Test
    void shouldUpdateBrand_WhenNameIsDifferent() {
        String uuid = "uuid-123";
        Brand existingBrand = new Brand(uuid, "OldName");
        UpdateBrandCommand command = new UpdateBrandCommand("NewName");

        when(brandRepository.findById(uuid)).thenReturn(Optional.of(existingBrand));
        when(brandRepository.save(any(Brand.class))).thenAnswer(i -> i.getArgument(0));

        Optional<Brand> result = updateBrand.updateBrand(uuid, command);

        assertTrue(result.isPresent());
        assertEquals("NewName", result.get().getName());

        verify(brandRepository, times(1)).save(any(Brand.class));
    }

    @Test
    void shouldNotCallSave_WhenNameIsSame() {
        String uuid = "uuid-123";
        Brand existingBrand = new Brand(uuid, "SameName");
        UpdateBrandCommand command = new UpdateBrandCommand("SameName");

        when(brandRepository.findById(uuid)).thenReturn(Optional.of(existingBrand));

        updateBrand.updateBrand(uuid, command);

        verify(brandRepository, never()).save(any(Brand.class));
    }
}
