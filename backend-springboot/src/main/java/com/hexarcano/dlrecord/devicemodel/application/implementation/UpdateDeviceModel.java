package com.hexarcano.dlrecord.devicemodel.application.implementation;

import java.util.Optional;

import com.hexarcano.dlrecord.brand.application.port.out.BrandRepositoryPort;
import com.hexarcano.dlrecord.brand.domain.model.Brand;
import com.hexarcano.dlrecord.devicemodel.application.port.in.UpdateDeviceModelUseCase;
import com.hexarcano.dlrecord.devicemodel.application.port.out.DeviceModelRepositoryPort;
import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;

import lombok.RequiredArgsConstructor;

/**
 * Use case implementation for updating an existing device model.
 * 
 * <p>
 * This class contains the business logic for the update process.
 * It implements the {@link UpdateDeviceModelUseCase} input port and uses the
 * {@link DeviceModelRepositoryPort} output port to persist the changes.
 * </p>
 */
@RequiredArgsConstructor
public class UpdateDeviceModel implements UpdateDeviceModelUseCase {
    private final DeviceModelRepositoryPort deviceModelRepository;
    private final BrandRepositoryPort brandRepository;

    /**
     * Updates an existing device model.
     * 
     * @param uuid        The unique ID of the device model to update.
     * @param deviceModel The device model containing the new data.
     * @return An {@link Optional} with the updated {@link DeviceModel}, or empty.
     */
    @Override
    public Optional<DeviceModel> updateDeviceModel(String uuid, DeviceModel deviceModel) {
        return deviceModelRepository.findById(uuid).map(modelToUpdate -> {
            if (deviceModel.getName() != null && !deviceModel.getName().equals(modelToUpdate.getName())) {
                modelToUpdate.changeName(deviceModel.getName());
            }

            if (deviceModel.getBrand() != null && deviceModel.getBrand().getUuid() != null
                    && !deviceModel.getBrand().getUuid().equals(modelToUpdate.getBrand().getUuid())) {
                Brand newBrand = brandRepository.findById(deviceModel.getBrand().getUuid())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Invalid brand reference."));

                modelToUpdate.changeBrand(newBrand);
            }

            return deviceModelRepository.save(modelToUpdate);
        });
    }
}
