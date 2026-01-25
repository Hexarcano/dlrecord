package com.hexarcano.dlrecord.brand.application.implementation;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.brand.application.port.in.RetrieveBrandUseCase;
import com.hexarcano.dlrecord.brand.application.port.out.BrandRepositoryPort;
import com.hexarcano.dlrecord.brand.domain.model.Brand;

import lombok.RequiredArgsConstructor;

/**
 * Use case implementation for retrieving one or more brands.
 * 
 * <p>
 * This class contains the business logic for creating brands.
 * It implements the {@link RetrieveBrandUseCase} input port and uses the
 * {@link BrandRepositoryPort} output port to fetch brand data.
 * </p>
 */
@RequiredArgsConstructor
public class RetrieveBrand implements RetrieveBrandUseCase {
    private final BrandRepositoryPort brandRepository;

    /**
     * Retrieves a single brand by its unique identifier.
     * 
     * @param uuid The unique ID of the brand to find.
     * @return An {@link Optional} containing the found {@link Brand}, or empty if
     *         not found.
     */
    @Override
    public Optional<Brand> findById(String uuid) {
        return brandRepository.findById(uuid);
    }

    /**
     * Retrieves a list of all brands.
     * 
     * @return A {@link List} of all {@link Brand} domain models.
     */
    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }
}
