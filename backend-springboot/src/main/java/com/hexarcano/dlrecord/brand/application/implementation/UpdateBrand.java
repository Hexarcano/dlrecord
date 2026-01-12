package com.hexarcano.dlrecord.brand.application.implementation;

import java.util.Optional;

import com.hexarcano.dlrecord.brand.application.port.in.IUpdateBrand;
import com.hexarcano.dlrecord.brand.application.port.out.IBrandRepository;
import com.hexarcano.dlrecord.brand.model.entity.Brand;

import lombok.AllArgsConstructor;

/**
 * Use case implementation for updating an existing brand.
 * 
 * <p>
 * This class contains the business logic for the update process.
 * It implements the {@link IUpdateBrand} input port and uses the
 * {@link IBrandRepository} output port to persist the changes.
 * </p>
 */
@AllArgsConstructor
public class UpdateBrand implements IUpdateBrand {
    private final IBrandRepository repository;

    /**
     * Updates an existing brand by its unique identifier.
     * 
     * @param uuid  The unique ID of the brand to update.
     * @param brand The {@link Brand} domain model containing the new values.
     * @return An {@link Optional} with the updated {@link Brand}, or empty if the
     *         brand was not found.
     */
    @Override
    public Optional<Brand> updateBrand(String uuid, Brand brand) {
        return repository.update(uuid, brand);
    }
}
