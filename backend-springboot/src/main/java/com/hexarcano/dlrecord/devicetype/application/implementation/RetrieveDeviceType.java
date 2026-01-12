package com.hexarcano.dlrecord.devicetype.application.implementation;

import com.hexarcano.dlrecord.devicetype.application.port.in.IRetrieveDeviceType;
import com.hexarcano.dlrecord.devicetype.application.port.out.IDeviceTypeRepository;
import com.hexarcano.dlrecord.devicetype.model.entity.DeviceType;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * Use case implementation for retrieving one or more device types.
 * <p>
 * This class contains the specific business logic for querying device types.
 * It implements the {@link IRetrieveDeviceType} input port and uses the {@link IDeviceTypeRepository}
 * output port to fetch brand data from the persistence layer.
 * </p>
 */
@AllArgsConstructor
public class RetrieveDeviceType implements IRetrieveDeviceType {
    private final IDeviceTypeRepository repository;

    /**
     * Retrieves a single device type by its unique identifier.
     * @param uuid The unique ID of the device type to find.
     * @return An {@link Optional} containing the found {@link DeviceType}, or empty if not found.
     */
    @Override
    public Optional<DeviceType> findById(String uuid) {
        return repository.findById(uuid);
    }

    /**
     * Retrieves a list of all device types.
     * @return A {@link List} of all {@link DeviceType} domain models. The list will be empty if none are found.
     */
    @Override
    public List<DeviceType> findAll() {
        return repository.findAll();
    }
}
