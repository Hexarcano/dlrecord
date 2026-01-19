package com.hexarcano.dlrecord.devicemodel.application.port.in;

import java.util.Optional;

import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;

/**
 * Input port (Use Case) for updating a device model.
 */
public interface UpdateDeviceModelUseCase {
    /**
     * Updates an existing device model.
     * 
     * @param uuid        The unique ID of the device model to update.
     * @param deviceModel The device model containing the new data.
     * @return An {@link Optional} with the updated device model, or empty.
     */
    Optional<DeviceModel> updateDeviceModel(String uuid, DeviceModel deviceModel);
}
