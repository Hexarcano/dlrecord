package com.hexarcano.dlrecord.devicemodel.application.implementation;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.devicemodel.application.port.in.RetrieveDeviceModelUseCase;
import com.hexarcano.dlrecord.devicemodel.application.port.out.DeviceModelRepositoryPort;
import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;

import lombok.AllArgsConstructor;

/**
 * Use case implementation for retrieving one or more device models.
 * 
 * <p>
 * This class contains the business logic for querying device models.
 * It implements the {@link RetrieveDeviceModelUseCase} input port and uses the
 * {@link DeviceModelRepositoryPort} output port to fetch device model data.
 * </p>
 */
@AllArgsConstructor
public class RetrieveDeviceModel implements RetrieveDeviceModelUseCase {
    private final DeviceModelRepositoryPort deviceModelRepository;

    /**
     * Retrieves a single device model by its unique identifier.
     * 
     * @param uuid The unique ID of the device model to find.
     * @return An {@link Optional} containing the found {@link DeviceModel}, or
     *         empty
     *         if not found.
     */
    @Override
    public Optional<DeviceModel> findById(String uuid) {
        return deviceModelRepository.findById(uuid);
    }

    /**
     * Retrieves a list of all device models.
     * 
     * @return A {@link List} of all {@link DeviceModel} domain models.
     */
    @Override
    public List<DeviceModel> findAll() {
        return deviceModelRepository.findAll();
    }
}
