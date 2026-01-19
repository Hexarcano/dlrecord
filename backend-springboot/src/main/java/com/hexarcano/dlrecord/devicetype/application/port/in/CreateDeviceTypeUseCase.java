package com.hexarcano.dlrecord.devicetype.application.port.in;

import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;

/**
 * Input port (Driving Port) for the 'Create DeviceType' use case.
 * Defines the contract for creating a new device brand within the application
 * core.
 */
public interface CreateDeviceTypeUseCase {
    /**
     * Executes the logic to create a new device type.
     * 
     * @param deviceType The device type object with the data to be created.
     * @return The newly created {@link DeviceType}.
     */
    DeviceType createDeviceType(DeviceType deviceType);
}
