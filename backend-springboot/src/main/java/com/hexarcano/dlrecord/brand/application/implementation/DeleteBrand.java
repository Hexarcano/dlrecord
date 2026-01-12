package com.hexarcano.dlrecord.brand.application.implementation;

import com.hexarcano.dlrecord.brand.application.port.in.IDeleteBrand;
import com.hexarcano.dlrecord.brand.application.port.out.IBrandRepository;

import lombok.AllArgsConstructor;

/**
 * Use case implementation for deleting a brand.
 * 
 * <p>
 * This class contains the business logic for the deletion process.
 * It implements the {@link IDeleteBrand} input port and uses the
 * {@link IBrandRepository} output port to delete the brand data.
 * </p>
 */
@AllArgsConstructor
public class DeleteBrand implements IDeleteBrand {
    private final IBrandRepository repository;

    /**
     * Deletes a brand by its unique identifier.
     * 
     * @param uuid The unique ID of the brand to be deleted.
     * @return {@code true} if the brand was successfully deleted,
     *         {@code false} otherwise.
     */
    @Override
    public boolean deleteBrand(String uuid) {
        return repository.deleteById(uuid);
    }
}
