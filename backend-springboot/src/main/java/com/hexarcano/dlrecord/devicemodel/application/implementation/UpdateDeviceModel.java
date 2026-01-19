package com.hexarcano.dlrecord.devicemodel.application.implementation;

import java.util.Optional;

import com.hexarcano.dlrecord.brand.application.port.out.BrandRepositoryPort;
import com.hexarcano.dlrecord.brand.domain.model.Brand;
import com.hexarcano.dlrecord.devicemodel.application.port.in.IUpdateDeviceModel;
import com.hexarcano.dlrecord.devicemodel.application.port.out.IDeviceModelRepository;
import com.hexarcano.dlrecord.devicemodel.model.entity.DeviceModel;

import lombok.AllArgsConstructor;

/**
 * Use case implementation for updating an existing device model.
 * 
 * <p>
 * This class contains the business logic for the update process.
 * It implements the {@link IUpdateDeviceModel} input port and uses the
 * {@link IDeviceModelRepository} output port to persist the changes.
 * </p>
 */
@AllArgsConstructor
public class UpdateDeviceModel implements IUpdateDeviceModel {
    private final IDeviceModelRepository deviceModelRepository;
    private final BrandRepositoryPort brandRepository;

    /**
     * Updates an existing device model by its unique identifier.
     * 
     * @param uuid       The unique ID of the device model to update.
     * @param newName    The new name for the device model (can be null if not changing).
     * @param newBrandId The new brand ID for the device model (can be null if not changing).
     * @return An {@link Optional} with the updated {@link DeviceModel}, or empty if
     *         the device model was not found.
     * @throws IllegalArgumentException if the new brand ID is provided but invalid.
     */
    @Override
    public Optional<DeviceModel> updateDeviceModel(String uuid, String newName, String newBrandId) {
        return deviceModelRepository.findById(uuid).map(modelToUpdate -> {
            if (newName != null && !newName.equals(modelToUpdate.getName())) {
                modelToUpdate.changeName(newName);
            }

            if (newBrandId != null && !newBrandId.equals(modelToUpdate.getBrand().getUuid())) {
                Brand newBrand = brandRepository.findById(newBrandId)
                        .orElseThrow(
                                () -> new IllegalArgumentException("Brand does not exist."));

                modelToUpdate.changeBrand(newBrand);
            }

            return deviceModelRepository.save(modelToUpdate);
        });
    }
}
