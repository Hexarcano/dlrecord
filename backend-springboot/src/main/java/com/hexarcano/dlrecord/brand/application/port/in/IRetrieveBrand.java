package com.hexarcano.dlrecord.brand.application.port.in;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.brand.model.entity.Brand;

/**
 * Input port (Driving Port) for the 'Retrieve Brand' use case.
 * Defines the contract for querying brand information from the application
 * core.
 */
public interface IRetrieveBrand {
    /**
     * Finds a brand by its unique identifier.
     *
     * @param uuid The unique identifier of the brand to find.
     * @return An {@link Optional} containing the {@link Brand} if found, or an
     *         empty Optional if not.
     */
    Optional<Brand> findById(String uuid);

    /**
     * Retrieves all brands.
     *
     * @return A {@link List} of all {@link Brand} objects.
     */
    List<Brand> findAll();
}
