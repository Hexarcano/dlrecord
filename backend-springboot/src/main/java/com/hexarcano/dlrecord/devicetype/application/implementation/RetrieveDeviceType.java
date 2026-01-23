package com.hexarcano.dlrecord.devicetype.application.implementation;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.devicetype.application.port.in.RetrieveDeviceTypeUseCase;
import com.hexarcano.dlrecord.devicetype.application.port.out.DeviceTypeRepositoryPort;
import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;

import lombok.RequiredArgsConstructor;

/**
 * Use case implementation for retrieving one or more device types.
 * 
 * <p>
 * This class contains the specific business logic for querying device types.
 * It implements the {@link IRetrieveDeviceType} input port and uses the
 * {@link IDeviceTypeRepository} output port to fetch device type data.
 * </p>
 */
@RequiredArgsConstructor
public class RetrieveDeviceType implements RetrieveDeviceTypeUseCase {
    private final DeviceTypeRepositoryPort deviceTypeRepository;

    /**
     * Retrieves a single device type by its unique identifier.
     * 
     * @param uuid The unique ID of the device type to find.
     * @return An {@link Optional} containing the found {@link DeviceType}, or empty
     *         if not found.
     */
    @Override
    public Optional<DeviceType> findById(String uuid) {
        return deviceTypeRepository.findById(uuid);
    }

    /**
     * Retrieves a list of all device types.
     * 
     * @return A {@link List} of all {@link DeviceType} domain models.
     */
    @Override
    public List<DeviceType> findAll() {
        return deviceTypeRepository.findAll();
    }
}
