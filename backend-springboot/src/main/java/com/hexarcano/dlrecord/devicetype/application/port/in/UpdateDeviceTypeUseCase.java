package com.hexarcano.dlrecord.devicetype.application.port.in;

import java.util.Optional;

import com.hexarcano.dlrecord.devicetype.application.port.in.command.UpdateDeviceTypeCommand;
import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;

/**
 * Input port (Driving Port) for the 'Update DeviceType' use case.
 * Defines the contract for updating an existing device type within the
 * application core.
 */
public interface UpdateDeviceTypeUseCase {
    /**
     * Executes the logic to update an existing device type.
     * 
     * @param uuid       The unique identifier of the device type to update.
     * @param deviceType The device type object containing the new data.
     * @return An {@link Optional} containing the updated {@link DeviceType} if
     *         found and updated, or an empty Optional otherwise.
     */
    Optional<DeviceType> updateDeviceType(String uuid, UpdateDeviceTypeCommand command);
}
