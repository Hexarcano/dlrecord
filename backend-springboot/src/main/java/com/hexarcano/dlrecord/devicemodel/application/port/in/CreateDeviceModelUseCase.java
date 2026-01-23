package com.hexarcano.dlrecord.devicemodel.application.port.in;

import com.hexarcano.dlrecord.devicemodel.application.port.in.command.CreateDeviceModelCommand;
import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;

/**
 * Input port (Use Case) for creating a new device model.
 */
public interface CreateDeviceModelUseCase {
    /**
     * Creates a new device model.
     * 
     * @param deviceModel The device model to create.
     * @return The created device model.
     */
    DeviceModel createDeviceModel(CreateDeviceModelCommand command);
}
