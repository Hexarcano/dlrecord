package com.hexarcano.dlrecord.devicetype.application.port.out;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;

/**
 * Output port (Driven Port) defining the contract for persistence operations on
 * the {@link DeviceType} entity.
 * 
 * <p>
 * This interface is implemented by an adapter in the infrastructure layer to
 * interact with a specific database.
 * </p>
 */
public interface DeviceTypeRepositoryPort {
    /**
     * Saves a new device type to the repository.
     *
     * @param deviceType The {@link DeviceType} entity to save.
     * @return The saved device type, usually with a repository-assigned ID.
     */
    DeviceType save(DeviceType deviceType);

    /**
     * Finds a device type by its unique identifier.
     *
     * @param uuid The ID of the device type to find.
     * @return An {@link Optional} containing the device type if found, or empty
     *         otherwise.
     */
    Optional<DeviceType> findById(String uuid);

    /**
     * Retrieves all device types from the repository with pagination.
     *
     * @param pageable The pagination information.
     * @return A {@link Page} of device types. May be empty if none exist.
     */
    Page<DeviceType> findAll(Pageable pageable);

    /**
     * Updates an existing device type in the repository.
     *
     * @param uuid       The unique identifier of the device type to update.
     * @param deviceType The {@link DeviceType} entity with updated data.
     * @return An {@link Optional} containing the updated device type if the
     *         operation was successful, or empty if not found.
     */
    Optional<DeviceType> update(String uuid, DeviceType deviceType);

    /**
     * Deletes a device type from the repository by its unique identifier.
     *
     * @param uuid The ID of the device type to delete.
     * @return {@code true} if the device type was successfully deleted,
     *         {@code false} otherwise.
     */
    boolean deleteById(String uuid);
}
