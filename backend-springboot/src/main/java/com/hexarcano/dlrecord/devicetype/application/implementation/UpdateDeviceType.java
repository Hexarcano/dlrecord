package com.hexarcano.dlrecord.devicetype.application.implementation;

import com.hexarcano.dlrecord.devicetype.application.port.in.IUpdateDeviceType;
import com.hexarcano.dlrecord.devicetype.application.port.out.IDeviceTypeRepository;
import com.hexarcano.dlrecord.devicetype.model.entity.DeviceType;
import lombok.AllArgsConstructor;

import java.util.Optional;

/**
 * Use case implementation for updating an existing device type.
 * <p>
 * This class contains the specific business logic for the update process.
 * It implements the {@link IUpdateDeviceType} input port and uses the {@link IDeviceTypeRepository}
 * output port to persist the changes.
 * </p>
 */
@AllArgsConstructor
public class UpdateDeviceType implements IUpdateDeviceType {
    private final IDeviceTypeRepository repository;

    /**
     * Updates an existing device type by its unique identifier.
     * <p>
     * Note: The validation of the device type's name is handled by the
     * {@link DeviceType} domain object itself upon its construction or modification.
     * </p>
     * @param uuid The unique ID of the device type to update.
     * @param deviceType The {@link DeviceType} domain model containing the new values.
     * @return An {@link Optional} with the updated {@link DeviceType}, or empty if the device type was not found.
     */
    @Override
    public Optional<DeviceType> updateDeviceType(String uuid, DeviceType deviceType) {
        return repository.update(uuid, deviceType);
    }
}
