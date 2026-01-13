package com.hexarcano.dlrecord.devicemodel.application.port.out;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.devicemodel.model.entity.DeviceModel;

/**
 * Output port for device model persistence.
 * Defines the operations necessary to interact with the data repository.
 */
public interface IDeviceModelRepository {
    /**
     * Saves a device model in the repository.
     *
     * @param deviceModel The device model entity to save.
     * @return The saved device model entity (may include generated fields like ID).
     */
    DeviceModel save(DeviceModel deviceModel);

    /**
     * Finds a device model by its unique identifier.
     *
     * @param uuid The unique identifier of the device model.
     * @return An Optional containing the device model if found, or empty if not.
     */
    Optional<DeviceModel> findById(String uuid);

    /**
     * Retrieves all device models from the repository.
     *
     * @return A list of all device models.
     */
    List<DeviceModel> findAll();

    /**
     * Deletes a device model by its unique identifier.
     *
     * @param uuid The unique identifier of the device model to delete.
     * @return true if the entity was successfully deleted, false otherwise.
     */
    boolean deleteById(String uuid);

    /**
     * Counts the number of device models associated with a specific brand.
     *
     * @param brandUuid The unique identifier of the brand.
     * @return The number of device models associated with the brand.
     */
    long countByBrandUuid(String brandUuid);
}
