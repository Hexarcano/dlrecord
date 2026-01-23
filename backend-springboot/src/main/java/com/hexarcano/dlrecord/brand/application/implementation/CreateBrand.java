package com.hexarcano.dlrecord.brand.application.implementation;

import com.hexarcano.dlrecord.brand.application.port.in.CreateBrandUseCase;
import com.hexarcano.dlrecord.brand.application.port.in.command.CreateBrandCommand;
import com.hexarcano.dlrecord.brand.application.port.out.BrandRepositoryPort;
import com.hexarcano.dlrecord.brand.domain.model.Brand;

import lombok.RequiredArgsConstructor;

/**
 * Use case implementation for creating a new brand.
 * 
 * <p>
 * This class contains the business logic for the creation process.
 * It implements the {@link CreateBrandUseCase} input port and uses the
 * {@link BrandRepositoryPort} output port to persist the brand data.
 * <p>
 */
@RequiredArgsConstructor
public class CreateBrand implements CreateBrandUseCase {
    private final BrandRepositoryPort brandRepository;

    /**
     * Creates a new brand.
     * 
     * @param brand The {@link Brand} domain model to create.
     * @return The created {@link Brand} domain model, typically with a new ID.
     */
    @Override
    public Brand createBrand(CreateBrandCommand command) {
        Brand brand = new Brand(null, command.name());

        return brandRepository.save(brand);
    }
}
