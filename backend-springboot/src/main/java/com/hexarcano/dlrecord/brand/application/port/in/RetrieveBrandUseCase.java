package com.hexarcano.dlrecord.brand.application.port.in;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hexarcano.dlrecord.brand.domain.model.Brand;

/**
 * Input port (Driving Port) for the 'Retrieve Brand' use case.
 * Defines the contract for querying brand information from the application
 * core.
 */
public interface RetrieveBrandUseCase {
    /**
     * Finds a brand by its unique identifier.
     *
     * @param uuid The unique identifier of the brand to find.
     * @return An {@link Optional} containing the {@link Brand} if found, or an
     *         empty Optional if not.
     */
    Optional<Brand> findById(String uuid);

    /**
     * Retrieves all brands with pagination.
     *
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link Brand} objects.
     */
    Page<Brand> findAll(Pageable pageable);
}
