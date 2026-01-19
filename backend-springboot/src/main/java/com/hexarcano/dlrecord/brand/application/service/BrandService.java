package com.hexarcano.dlrecord.brand.application.service;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.brand.application.port.in.CreateBrandUseCase;
import com.hexarcano.dlrecord.brand.application.port.in.DeleteBrandUseCase;
import com.hexarcano.dlrecord.brand.application.port.in.RetrieveBrandUseCase;
import com.hexarcano.dlrecord.brand.application.port.in.UpdateBrandUseCase;
import com.hexarcano.dlrecord.brand.domain.model.Brand;

import lombok.RequiredArgsConstructor;

/**
 * A service facade that aggregates all brand-related use cases into a single
 * entry point.
 * 
 * <p>
 * This class implements all the input port interfaces
 * ({@link CreateBrandUseCase}, {@link RetrieveBrandUseCase}, etc.) and
 * delegates the calls to the actual use case implementations. It is used by the
 * primary adapter (e.g., {@code BrandController}) to interact with the
 * application's core logic.
 * </p>
 */
@RequiredArgsConstructor
public class BrandService implements CreateBrandUseCase, RetrieveBrandUseCase, UpdateBrandUseCase, DeleteBrandUseCase {
    private final CreateBrandUseCase createBrand;
    private final RetrieveBrandUseCase retrieveBrand;
    private final UpdateBrandUseCase updateBrand;
    private final DeleteBrandUseCase deleteBrand;

    /**
     * Delegates the call to the 'Create Brand' use case implementation.
     * 
     * @param brand The brand to create.
     * @return The created brand.
     */
    @Override
    public Brand createBrand(Brand brand) {
        return createBrand.createBrand(brand);
    }

    /**
     * Delegates the call to the 'Retrieve Brand' use case to find a brand by its
     * ID.
     * 
     * @param uuid The unique ID of the brand to find.
     * @return An {@link Optional} with the found brand, or empty.
     */
    @Override
    public Optional<Brand> findById(String uuid) {
        return retrieveBrand.findById(uuid);
    }

    /**
     * Delegates the call to the 'Retrieve Brand' use case to find all brands.
     * 
     * @return A list of all brands.
     */
    @Override
    public List<Brand> findAll() {
        return retrieveBrand.findAll();
    }

    /**
     * Delegates the call to the 'Update Brand' use case.
     * 
     * @param uuid  The unique ID of the brand to update.
     * @param brand The brand data to update.
     * @return An {@link Optional} with the updated brand, or empty.
     */
    @Override
    public Optional<Brand> updateBrand(String uuid, Brand brand) {
        return updateBrand.updateBrand(uuid, brand);
    }

    /**
     * Delegates the call to the 'Delete Brand' use case.
     * 
     * @param uuid The unique ID of the brand to delete.
     * @return {@code true} if deleted, {@code false} otherwise.
     */
    @Override
    public boolean deleteBrand(String uuid) {
        return deleteBrand.deleteBrand(uuid);
    }
}
