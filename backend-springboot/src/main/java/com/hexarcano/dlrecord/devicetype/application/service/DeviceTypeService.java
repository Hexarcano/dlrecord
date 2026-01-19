package com.hexarcano.dlrecord.devicetype.application.service;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.devicetype.application.port.in.CreateDeviceTypeUseCase;
import com.hexarcano.dlrecord.devicetype.application.port.in.DeleteDeviceTypeUseCase;
import com.hexarcano.dlrecord.devicetype.application.port.in.RetrieveDeviceTypeUseCase;
import com.hexarcano.dlrecord.devicetype.application.port.in.UpdateDeviceTypeUseCase;
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
    public DeviceType createDeviceType(DeviceType deviceType) {
        return createDeviceType.createDeviceType(deviceType);
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
     * types.
     * 
     * @return A list of all device types.
     */
    @Override
    public List<DeviceType> findAll() {
        return retrieveDeviceType.findAll();
    }

    /**
     * Delegates the call to the 'Update DeviceType' use case.
     * 
     * @param uuid       The unique ID of the device type to update.
     * @param deviceType The device type data to update.
     * @return An {@link Optional} with the updated device type, or empty.
     */
    @Override
    public Optional<DeviceType> updateDeviceType(String uuid, DeviceType deviceType) {
        return updateDeviceType.updateDeviceType(uuid, deviceType);
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
