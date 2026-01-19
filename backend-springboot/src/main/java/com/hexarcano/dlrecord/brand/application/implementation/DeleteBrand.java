package com.hexarcano.dlrecord.brand.application.implementation;

import com.hexarcano.dlrecord.brand.application.port.in.DeleteBrandUseCase;
import com.hexarcano.dlrecord.brand.application.port.out.BrandRepositoryPort;
import com.hexarcano.dlrecord.devicemodel.application.port.out.IDeviceModelRepository;
import com.hexarcano.dlrecord.exception.DataConflictException;

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
public class DeleteBrand implements DeleteBrandUseCase {
    private final BrandRepositoryPort brandRepository;
    private final IDeviceModelRepository deviceModelRepository;

    /**
     * Deletes a brand by its unique identifier.
     * 
     * @param uuid The unique ID of the brand to be deleted.
     * @return {@code true} if the brand was successfully deleted,
     *         {@code false} otherwise.
     * @throws DataConflictException if brand has associated device models.
     */
    @Override
    public boolean deleteBrand(String uuid) {
        if (deviceModelRepository.countByBrandUuid(uuid) > 0) {
            throw new DataConflictException("Cannot delete brand. Delete associated device models first.");
        }

        return brandRepository.deleteById(uuid);
    }
}
