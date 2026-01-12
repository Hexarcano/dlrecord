package com.hexarcano.dlrecord.brand.application.implementation;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.brand.application.port.in.IRetrieveBrand;
import com.hexarcano.dlrecord.brand.application.port.out.IBrandRepository;
import com.hexarcano.dlrecord.brand.model.entity.Brand;

import lombok.AllArgsConstructor;

/**
 * Use case implementation for retrieving one or more brands.
 * This class contains the business logic for querying brands.
 * It implements the {@link IRetrieveBrand} input port and uses the {@link IBrandRepository} output port
 * to fetch brand data.
 */
@AllArgsConstructor
public class RetrieveBrand implements IRetrieveBrand {
    private final IBrandRepository repository;

    /**
     * Retrieves a single brand by its unique identifier.
     * @param uuid The unique ID of the brand to find.
     * @return An {@link Optional} containing the found {@link Brand}, or empty if not found.
     */
    @Override
    public Optional<Brand> findById(String uuid) {
        return repository.findById(uuid);
    }

    /**
     * Retrieves a list of all brands.
     * @return A {@link List} of all {@link Brand} domain models.
     */
    @Override
    public List<Brand> findAll() {
        return repository.findAll();
    }
}
