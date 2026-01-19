package com.hexarcano.dlrecord.devicemodel.application.port.in;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;

/**
 * Input port (Use Case) for retrieving device models.
 */
public interface RetrieveDeviceModelUseCase {
    /**
     * Finds a device model by its unique identifier.
     * 
     * @param uuid The unique ID of the device model.
     * @return An {@link Optional} containing the device model if found, or empty.
     */
    Optional<DeviceModel> findById(String uuid);

    /**
     * Retrieves all device models.
     * 
     * @return A list of all device models.
     */
    List<DeviceModel> findAll();
}
