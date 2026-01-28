package com.hexarcano.dlrecord.brand.application.implementation;

import org.springframework.stereotype.Service;

import com.hexarcano.dlrecord.brand.application.port.in.CreateBrandUseCase;
import com.hexarcano.dlrecord.brand.application.port.in.command.CreateBrandCommand;
import com.hexarcano.dlrecord.brand.application.port.out.BrandRepositoryPort;
import com.hexarcano.dlrecord.brand.domain.exception.BrandAlreadyExistsException;
import com.hexarcano.dlrecord.brand.domain.model.Brand;

import lombok.RequiredArgsConstructor;

/**
 * Use case implementation for creating a new brand.
 * 
 * <p>
 * This class contains the business logic for creation process.
 * It implements the {@link CreateBrandUseCase} input port and uses the
 * {@link BrandRepositoryPort} output port to persist brand data.
 * <p>
 */
@Service
@RequiredArgsConstructor
public class CreateBrand implements CreateBrandUseCase {
    private final BrandRepositoryPort brandRepository;

    /**
     * Creates a new brand.
     * 
     * @param command The {@link CreateBrandCommand} containing data to create
     *                brand.
     * @return The created {@link Brand} domain model, typically with a new ID.
     * @throws BrandAlreadyExistsException if a brand with the same name already
     *                                     exists.
     */
    @Override
    public Brand createBrand(CreateBrandCommand command) {
        if (brandRepository.existsByName(command.name())) {
            throw BrandAlreadyExistsException.ofName(command.name());
        }

        Brand brand = new Brand(null, command.name());

        return brandRepository.save(brand);
    }
}
