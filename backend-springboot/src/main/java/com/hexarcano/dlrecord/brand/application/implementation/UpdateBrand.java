package com.hexarcano.dlrecord.brand.application.implementation;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hexarcano.dlrecord.brand.application.port.in.UpdateBrandUseCase;
import com.hexarcano.dlrecord.brand.application.port.in.command.UpdateBrandCommand;
import com.hexarcano.dlrecord.brand.application.port.out.BrandRepositoryPort;
import com.hexarcano.dlrecord.brand.domain.exception.BrandAlreadyExistsException;
import com.hexarcano.dlrecord.brand.domain.exception.BrandNotFoundException;
import com.hexarcano.dlrecord.brand.domain.model.Brand;

import lombok.RequiredArgsConstructor;

/**
 * Use case implementation for updating an existing brand.
 * 
 * <p>
 * This class contains the business logic for update process.
 * It implements the {@link UpdateBrandUseCase} input port and uses the
 * {@link BrandRepositoryPort} output port to persist changes.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class UpdateBrand implements UpdateBrandUseCase {
    private final BrandRepositoryPort brandRepository;

    /**
     * Updates an existing brand by its unique identifier.
     * 
     * @param uuid    The unique ID of brand to update.
     * @param command The {@link UpdateBrandCommand} containing new values.
     * @return An {@link Optional} with updated {@link Brand}, or empty if
     *         brand was not found.
     * @throws BrandNotFoundException      if brand with given UUID does not exist.
     * @throws BrandAlreadyExistsException if new name already exists for another
     *                                     brand.
     */
    @Override
    public Optional<Brand> updateBrand(String uuid, UpdateBrandCommand command) {
        if (!brandRepository.existsById(uuid)) {
            throw BrandNotFoundException.ofUuid(uuid);
        }

        if (command.name() != null && brandRepository.existsByName(command.name())) {
            throw BrandAlreadyExistsException.ofName(command.name());
        }

        return brandRepository.findById(uuid).map(brandToUpdate -> {
            if (command.name() != null && !command.name().equals(brandToUpdate.getName())) {
                brandToUpdate.changeName(command.name());

                return brandRepository.save(brandToUpdate);
            }

            return brandToUpdate;
        });
    }
}
