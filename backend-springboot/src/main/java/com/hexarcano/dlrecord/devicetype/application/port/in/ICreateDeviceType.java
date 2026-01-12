package com.hexarcano.dlrecord.devicetype.application.port.in;

import com.hexarcano.dlrecord.devicetype.model.entity.DeviceType;

/**
 * Input port (Driving Port) for the 'Create DeviceType' use case.
 */
public interface ICreateDeviceType {
    /**
     * Executes the logic to create a new device type.
     * @param deviceType The device type object with the data to be created.
     * @return The newly created {@link DeviceType}.
     */
    DeviceType createDeviceType(DeviceType deviceType);
}
