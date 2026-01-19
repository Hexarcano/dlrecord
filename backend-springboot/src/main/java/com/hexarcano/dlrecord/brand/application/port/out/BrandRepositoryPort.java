package com.hexarcano.dlrecord.brand.application.port.out;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.brand.domain.model.Brand;

/**
 * Output port (Driven Port) defining the contract for persistence operations on
 * the {@link Brand} entity.
 * 
 * <p>
 * This interface is implemented by an adapter in the infrastructure layer to
 * interact with a specific database.
 * </p>
 */
public interface BrandRepositoryPort {
    /**
     * Saves a new brand to the repository.
     *
     * @param brand The {@link Brand} entity to save.
     * @return The saved brand, usually with a repository-assigned ID.
     */
    Brand save(Brand brand);

    /**
     * Finds a brand by its unique identifier.
     *
     * @param uuid The ID of the brand to find.
     * @return An {@link Optional} containing the brand if found, or empty
     *         otherwise.
     */
    Optional<Brand> findById(String uuid);

    /**
     * Retrieves all brands from the repository.
     *
     * @return A {@link List} of all brands. May be empty if none exist.
     */
    List<Brand> findAll();

    /**
     * Updates an existing brand in the repository.
     *
     * @param brand The {@link Brand} entity with updated data.
     * @return An {@link Optional} containing the updated brand if the operation was
     *         successful, or empty if the brand to update was not found.
     */
    Optional<Brand> update(String uuid, Brand brand);

    /**
     * Deletes a brand from the repository by its unique identifier.
     *
     * @param uuid The ID of the brand to delete.
     * @return {@code true} if the brand was successfully deleted,
     *         {@code false} otherwise.
     */
    boolean deleteById(String uuid);

    /**
     * Checks if a brand exists in the repository by its unique identifier.
     *
     * @param uuid The ID of the brand to check.
     * @return {@code true} if the brand exists, {@code false} otherwise.
     */
    boolean existsById(String uuid);
}
