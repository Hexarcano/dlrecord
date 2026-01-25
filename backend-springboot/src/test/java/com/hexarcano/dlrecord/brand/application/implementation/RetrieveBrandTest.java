package com.hexarcano.dlrecord.brand.application.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexarcano.dlrecord.brand.application.port.out.BrandRepositoryPort;
import com.hexarcano.dlrecord.brand.domain.model.Brand;

@ExtendWith(MockitoExtension.class)
class RetrieveBrandTest {

    @Mock
    private BrandRepositoryPort brandRepository;

    @InjectMocks
    private RetrieveBrand retrieveBrand;

    @Test
    void shouldReturnBrand_WhenExists() {
        String uuid = "uuid-123";
        Brand expectedBrand = new Brand(uuid, "Sony");
        when(brandRepository.findById(uuid)).thenReturn(Optional.of(expectedBrand));

        Optional<Brand> result = retrieveBrand.findById(uuid);

        assertTrue(result.isPresent());
        assertEquals("Sony", result.get().getName());
    }

    @Test
    void shouldReturnEmpty_WhenDoesNotExist() {
        String uuid = "uuid-404";
        when(brandRepository.findById(uuid)).thenReturn(Optional.empty());

        Optional<Brand> result = retrieveBrand.findById(uuid);

        assertTrue(result.isEmpty());
    }
}
