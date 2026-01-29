package com.hexarcano.dlrecord.devicetype.application.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hexarcano.dlrecord.devicetype.application.port.in.CreateDeviceTypeUseCase;
import com.hexarcano.dlrecord.devicetype.application.port.in.DeleteDeviceTypeUseCase;
import com.hexarcano.dlrecord.devicetype.application.port.in.RetrieveDeviceTypeUseCase;
import com.hexarcano.dlrecord.devicetype.application.port.in.UpdateDeviceTypeUseCase;
import com.hexarcano.dlrecord.devicetype.application.port.in.command.CreateDeviceTypeCommand;
import com.hexarcano.dlrecord.devicetype.application.port.in.command.UpdateDeviceTypeCommand;
import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;

import lombok.RequiredArgsConstructor;

/**
 * A service facade that aggregates all device-type-related use cases into a
 * single entry point.
 * 
 * <p>
 * This class implements all the input port interfaces
 * ({@link ICreateDeviceType}, {@link IRetrieveDeviceType}, etc.) and delegates
 * the calls to the actual use case implementations. It is used by the primary
 * adapter (e.g., {@code DeviceTypeController}) to interact with the
 * application's core logic.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class DeviceTypeService implements CreateDeviceTypeUseCase, RetrieveDeviceTypeUseCase, UpdateDeviceTypeUseCase,
        DeleteDeviceTypeUseCase {
    private final CreateDeviceTypeUseCase createDeviceType;
    private final RetrieveDeviceTypeUseCase retrieveDeviceType;
    private final UpdateDeviceTypeUseCase updateDeviceType;
    private final DeleteDeviceTypeUseCase deleteDeviceType;

    /**
     * Delegates the call to the 'Create DeviceType' use case implementation.
     * 
     * @param deviceType The device type to create.
     * @return The created device type.
     */
    @Override
    public DeviceType createDeviceType(CreateDeviceTypeCommand command) {
        return createDeviceType.createDeviceType(command);
    }

    /**
     * Delegates the call to the 'Retrieve DeviceType' use case to find a device
     * type by its ID.
     * 
     * @param uuid The unique ID of the device type to find.
     * @return An {@link Optional} with the found device type, or empty.
     */
    @Override
    public Optional<DeviceType> findById(String uuid) {
        return retrieveDeviceType.findById(uuid);
    }

    /**
     * Delegates the call to the 'Retrieve DeviceType' use case to find all device
     * types with pagination.
     * 
     * @param pageable The pagination information.
     * @return A page of all device types.
     */
    @Override
    public Page<DeviceType> findAll(Pageable pageable) {
        return retrieveDeviceType.findAll(pageable);
    }

    /**
     * Delegates the call to the 'Update DeviceType' use case.
     * 
     * @param uuid    The unique ID of the device type to update.
     * @param command The command containing the new data.
     * @return An {@link Optional} with the updated device type, or empty.
     */
    @Override
    public Optional<DeviceType> updateDeviceType(String uuid, UpdateDeviceTypeCommand command) {
        return updateDeviceType.updateDeviceType(uuid, command);
    }

    /**
     * Delegates the call to the 'Delete DeviceType' use case.
     * 
     * @param uuid The unique ID of the device type to delete.
     * @return {@code true} if deleted, {@code false} otherwise.
     */
    @Override
    public boolean deleteDeviceType(String uuid) {
        return deleteDeviceType.deleteDeviceType(uuid);
    }
}
