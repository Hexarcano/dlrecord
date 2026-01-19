package com.hexarcano.dlrecord.devicemodel.application.port.out;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;

/**
 * Output port (Repository) for managing device models.
 */
public interface DeviceModelRepositoryPort {
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
     * Checks if a device model with the given unique identifier exists.
     *
     * @param uuid The unique identifier of the device model.
     * @return true if a device model with the given UUID exists, false otherwise.
     */
    boolean existsById(String uuid);

    /**
     * Checks if a device model with the given name exists.
     *
     * @param name The name of the device model.
     * @return true if a device model with the given name exists, false otherwise.
     */
    boolean existsByName(String name);

    /**
     * Counts the number of device models associated with a specific brand.
     *
     * @param brandUuid The unique identifier of the brand.
     * @return The number of device models associated with the brand.
     */
    long countByBrandUuid(String brandUuid);
}
