package com.hexarcano.dlrecord.devicemodel.application.implementation;

import com.hexarcano.dlrecord.devicemodel.application.port.in.IDeleteDeviceModel;
import com.hexarcano.dlrecord.devicemodel.application.port.out.IDeviceModelRepository;

import lombok.AllArgsConstructor;

/**
 * Use case implementation for deleting a device model.
 * 
 * <p>
 * This class contains the business logic for the deletion process.
 * It implements the {@link IDeleteDeviceModel} input port and uses the
 * {@link IDeviceModelRepository} output port to delete the device model data.
 * </p>
 */
@AllArgsConstructor
public class DeleteDeviceModel implements IDeleteDeviceModel {
    private final IDeviceModelRepository repository;

    /**
     * Deletes a device model by its unique identifier.
     * 
     * @param uuid The unique ID of the device model to be deleted.
     * @return {@code true} if the device model was successfully deleted,
     *         {@code false} otherwise.
     */
    @Override
    public boolean deleteDeviceModel(String uuid) {
        return repository.deleteById(uuid);
    }
}
