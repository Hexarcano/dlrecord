package com.hexarcano.dlrecord.devicetype.application.implementation;

import java.util.Optional;

import com.hexarcano.dlrecord.devicetype.application.port.in.UpdateDeviceTypeUseCase;
import com.hexarcano.dlrecord.devicetype.application.port.in.command.UpdateDeviceTypeCommand;
import com.hexarcano.dlrecord.devicetype.application.port.out.DeviceTypeRepositoryPort;
import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;

import lombok.RequiredArgsConstructor;

/**
 * Use case implementation for updating an existing device type.
 * 
 * <p>
 * This class contains the specific business logic for the update process.
 * It implements the {@link IUpdateDeviceType} input port and uses the
 * {@link IDeviceTypeRepository} output port to persist the changes.
 * </p>
 */
@RequiredArgsConstructor
public class UpdateDeviceType implements UpdateDeviceTypeUseCase {
    private final DeviceTypeRepositoryPort deviceTypeRepository;

    /**
     * Updates an existing device type by its unique identifier.
     * 
     * @param uuid    The unique ID of the device type to update.
     * @param command The command containing the new data.
     * @return An {@link Optional} with the updated {@link DeviceType}, or empty if
     *         the device type was not found.
     */
    @Override
    public Optional<DeviceType> updateDeviceType(String uuid, UpdateDeviceTypeCommand command) {
        return deviceTypeRepository.findById(uuid).map(deviceTypeToUpdate -> {
            if (command.name() != null && !command.name().equals(deviceTypeToUpdate.getName())) {
                deviceTypeToUpdate.changeName(command.name());
            }

            return deviceTypeRepository.save(deviceTypeToUpdate);
        });
    }
}
