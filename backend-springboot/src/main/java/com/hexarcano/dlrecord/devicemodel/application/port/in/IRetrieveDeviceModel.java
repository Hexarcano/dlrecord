package com.hexarcano.dlrecord.devicemodel.application.port.in;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.devicemodel.model.entity.DeviceModel;

/**
 * Input port for the device model retrieval use case.
 */
public interface IRetrieveDeviceModel {
    /**
     * Finds a device model by its unique identifier.
     *
     * @param uuid The unique identifier of the device model.
     * @return An Optional containing the device model if found, or empty if not.
     */
    Optional<DeviceModel> findById(String uuid);

    /**
     * Retrieves all existing device models.
     *
     * @return A list of all device models.
     */
    List<DeviceModel> findAll();
}
