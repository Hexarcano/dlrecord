package com.hexarcano.dlrecord.devicetype.application.port.in;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.devicetype.model.entity.DeviceType;

/**
 * Input port (Driving Port) for the 'Retrieve DeviceType' use case.
 * Defines the contract for querying device type information from the
 * application core.
 */
public interface IRetrieveDeviceType {
    /**
     * Finds a device type by its unique identifier.
     * 
     * @param uuid The unique identifier of the device type to find.
     * @return An {@link Optional} containing the {@link DeviceType} if found, or an
     *         empty Optional if not.
     */
    Optional<DeviceType> findById(String uuid);

    /**
     * Retrieves all device types.
     * 
     * @return A {@link List} of all {@link DeviceType} objects.
     */
    List<DeviceType> findAll();
}
