package com.hexarcano.dlrecord.brand.application.implementation;

import java.util.Optional;

import com.hexarcano.dlrecord.brand.application.port.in.UpdateBrandUseCase;
import com.hexarcano.dlrecord.brand.application.port.in.command.UpdateBrandCommand;
import com.hexarcano.dlrecord.brand.application.port.out.BrandRepositoryPort;
import com.hexarcano.dlrecord.brand.domain.model.Brand;

import lombok.RequiredArgsConstructor;

/**
 * Use case implementation for updating an existing brand.
 * 
 * <p>
 * This class contains the business logic for the update process.
 * It implements the {@link IUpdateBrand} input port and uses the
 * {@link IBrandRepository} output port to persist the changes.
 * </p>
 */
@RequiredArgsConstructor
public class UpdateBrand implements UpdateBrandUseCase {
    private final BrandRepositoryPort brandRepository;

    /**
     * Updates an existing brand by its unique identifier.
     * 
     * @param uuid  The unique ID of the brand to update.
     * @param brand The {@link Brand} domain model containing the new values.
     * @return An {@link Optional} with the updated {@link Brand}, or empty if the
     *         brand was not found.
     */
    @Override
    public Optional<Brand> updateBrand(String uuid, UpdateBrandCommand command) {
        return brandRepository.findById(uuid).map(brandToUpdate -> {
            if (command.name() != null && !command.name().equals(brandToUpdate.getName())) {
                brandToUpdate.changeName(command.name());

                return brandRepository.save(brandToUpdate);
            }

            return brandToUpdate;
        });
    }
}
