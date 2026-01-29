package com.hexarcano.dlrecord.devicetype.application.port.in;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;

/**
 * Input port (Driving Port) for the 'Retrieve DeviceType' use case.
 * Defines the contract for querying device type information from the
 * application core.
 */
public interface RetrieveDeviceTypeUseCase {
    /**
     * Finds a device type by its unique identifier.
     * 
     * @param uuid The unique identifier of the device type to find.
     * @return An {@link Optional} containing the {@link DeviceType} if found, or an
     *         empty Optional if not.
     */
    Optional<DeviceType> findById(String uuid);

    /**
     * Retrieves all device types with pagination.
     * 
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link DeviceType} objects.
     */
    Page<DeviceType> findAll(Pageable pageable);
}
