package com.hexarcano.dlrecord.brand.application.implementation;

import com.hexarcano.dlrecord.brand.application.port.in.ICreateBrand;
import com.hexarcano.dlrecord.brand.application.port.out.IBrandRepository;
import com.hexarcano.dlrecord.brand.model.entity.Brand;

import lombok.AllArgsConstructor;

/**
 * Use case implementation for creating a new brand.
 * This class contains the business logic for the creation process.
 * It implements the {@link ICreateBrand} input port and uses the {@link IBrandRepository} output port
 * to persist the brand data.
 */
@AllArgsConstructor
public class CreateBrand implements ICreateBrand {
    private final IBrandRepository repository;

    /**
     * Creates a new brand by persisting it through the repository port.
     * @param brand The {@link Brand} domain model to create.
     * @return The created {@link Brand} domain model, typically with a new ID.
     */
    @Override
    public Brand createBrand(Brand brand) {
        return repository.save(brand);
    }
}
