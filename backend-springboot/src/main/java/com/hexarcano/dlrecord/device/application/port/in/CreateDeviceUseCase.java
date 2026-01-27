package com.hexarcano.dlrecord.device.application.port.in;

import com.hexarcano.dlrecord.device.application.port.in.command.CreateDeviceCommand;
import com.hexarcano.dlrecord.device.domain.model.Device;

public interface CreateDeviceUseCase {
    Device createDevice(CreateDeviceCommand command);
}
