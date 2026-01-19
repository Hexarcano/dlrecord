package com.hexarcano.dlrecord.devicemodel.application.implementation;

import com.hexarcano.dlrecord.devicemodel.application.port.in.DeleteDeviceModelUseCase;
import com.hexarcano.dlrecord.devicemodel.application.port.out.DeviceModelRepositoryPort;

import lombok.AllArgsConstructor;

/**
 * Use case implementation for deleting a device model.
 * 
 * <p>
 * This class contains the business logic for the deletion process.
 * It implements the {@link DeleteDeviceModelUseCase} input port and uses the
 * {@link DeviceModelRepositoryPort} output port to delete the device model
 * data.
 * </p>
 */
@AllArgsConstructor
public class DeleteDeviceModel implements DeleteDeviceModelUseCase {
    private final DeviceModelRepositoryPort deviceModelRepository;

    /**
     * Deletes a device model by its unique identifier.
     * 
     * @param uuid The unique ID of the device model to be deleted.
     * @return {@code true} if the device model was successfully deleted,
     *         {@code false} otherwise.
     */
    @Override
    public boolean deleteDeviceModel(String uuid) {
        return deviceModelRepository.deleteById(uuid);
    }
}
