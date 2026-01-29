package com.hexarcano.dlrecord.brand.application.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexarcano.dlrecord.brand.application.port.in.command.UpdateBrandCommand;
import com.hexarcano.dlrecord.brand.application.port.out.BrandRepositoryPort;
import com.hexarcano.dlrecord.brand.domain.exception.BrandAlreadyExistsException;
import com.hexarcano.dlrecord.brand.domain.exception.BrandNotFoundException;
import com.hexarcano.dlrecord.brand.domain.model.Brand;

@ExtendWith(MockitoExtension.class)
class UpdateBrandTest {

    @Mock
    private BrandRepositoryPort brandRepository;

    @InjectMocks
    private UpdateBrand updateBrand;

    @Test
    @DisplayName("should update brand when name is different")
    void shouldUpdateBrand_WhenNameIsDifferent() {
        String uuid = "uuid-123";
        Brand existingBrand = new Brand(uuid, "OldName");
        UpdateBrandCommand command = new UpdateBrandCommand("NewName");

        when(brandRepository.existsById(uuid)).thenReturn(true);
        when(brandRepository.existsByName("NewName")).thenReturn(false);
        when(brandRepository.findById(uuid)).thenReturn(Optional.of(existingBrand));
        when(brandRepository.save(any(Brand.class))).thenAnswer(i -> i.getArgument(0));

        Optional<Brand> result = updateBrand.updateBrand(uuid, command);

        assertTrue(result.isPresent());
        assertEquals("NewName", result.get().getName());

        verify(brandRepository, times(1)).save(any(Brand.class));
    }

    @Test
    @DisplayName("should not call save when name is same")
    void shouldNotCallSave_WhenNameIsSame() {
        String uuid = "uuid-123";
        Brand existingBrand = new Brand(uuid, "SameName");
        UpdateBrandCommand command = new UpdateBrandCommand("SameName");

        when(brandRepository.existsById(uuid)).thenReturn(true);
        when(brandRepository.findById(uuid)).thenReturn(Optional.of(existingBrand));

        updateBrand.updateBrand(uuid, command);

        verify(brandRepository, never()).save(any(Brand.class));
    }

    @ParameterizedTest
    @CsvSource({
            "''",
            "Invalid123",
            "Invalid_Smart",
            "Invalid@Smart"
    })
    @DisplayName("should throw exception when name is invalid")
    void shouldThrowException_WhenNameIsInvalid(String invalidName) {
        String uuid = "uuid-123";
        Brand existingBrand = new Brand(uuid, "ValidName");
        UpdateBrandCommand command = new UpdateBrandCommand(invalidName);

        when(brandRepository.existsById(uuid)).thenReturn(true);
        when(brandRepository.findById(uuid)).thenReturn(Optional.of(existingBrand));

        assertThrows(IllegalArgumentException.class, () -> updateBrand.updateBrand(uuid, command));

        verify(brandRepository, never()).save(any(Brand.class));
    }

    @Test
    @DisplayName("should throw BrandNotFoundException when brand does not exist")
    void shouldThrowBrandNotFoundException_WhenBrandDoesNotExist() {
        String uuid = "non-existent-uuid";
        UpdateBrandCommand command = new UpdateBrandCommand("NewName");

        when(brandRepository.existsById(uuid)).thenReturn(false);

        assertThrows(BrandNotFoundException.class, () -> updateBrand.updateBrand(uuid, command));

        verify(brandRepository, never()).save(any(Brand.class));
    }

    @Test
    @DisplayName("should throw BrandAlreadyExistsException when name already exists")
    void shouldThrowBrandAlreadyExistsException_WhenNameAlreadyExists() {
        String uuid = "uuid-123";
        UpdateBrandCommand command = new UpdateBrandCommand("ExistingName");

        when(brandRepository.existsById(uuid)).thenReturn(true);
        when(brandRepository.existsByName("ExistingName")).thenReturn(true);

        assertThrows(BrandAlreadyExistsException.class, () -> updateBrand.updateBrand(uuid, command));

        verify(brandRepository, never()).save(any(Brand.class));
    }
}
