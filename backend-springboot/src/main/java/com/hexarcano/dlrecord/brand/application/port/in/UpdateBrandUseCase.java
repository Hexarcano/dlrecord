package com.hexarcano.dlrecord.brand.application.port.in;

import java.util.Optional;

import com.hexarcano.dlrecord.brand.domain.model.Brand;

/**
 * Input port (Driving Port) for the 'Update Brand' use case.
 * Defines the contract for updating an existing brand within the application
 * core.
 */
public interface UpdateBrandUseCase {
    /**
     * Executes the logic to update an existing brand.
     *
     * @param uuid  The unique identifier of the brand to update.
     * @param brand The brand object containing the new data.
     * @return An {@link Optional} containing the updated {@link Brand} if found and
     *         updated, or an empty Optional otherwise.
     */
    Optional<Brand> updateBrand(String uuid, Brand brand);
}
