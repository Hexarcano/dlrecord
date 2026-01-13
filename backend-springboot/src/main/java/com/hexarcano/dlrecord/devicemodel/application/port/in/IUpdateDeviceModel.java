package com.hexarcano.dlrecord.devicemodel.application.port.in;

import java.util.Optional;

import com.hexarcano.dlrecord.devicemodel.model.entity.DeviceModel;

/**
 * Input port for the device model update use case.
 */
public interface IUpdateDeviceModel {
    /**
     * Updates the information of an existing device model.
     *
     * @param uuid       The unique identifier of the device model to update.
     * @param newName    The new name for the device model.
     * @param newBrandId The new identifier of the associated brand.
     * @return An Optional containing the updated device model if the operation was
     *         successful.
     */
    Optional<DeviceModel> updateDeviceModel(String uuid, String newName, String newBrandId);
}
