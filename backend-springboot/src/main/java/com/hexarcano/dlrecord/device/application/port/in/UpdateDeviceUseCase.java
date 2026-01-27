package com.hexarcano.dlrecord.device.application.port.in;

import com.hexarcano.dlrecord.device.application.port.in.command.UpdateDeviceCommand;
import com.hexarcano.dlrecord.device.domain.model.Device;

public interface UpdateDeviceUseCase {
    Device updateDevice(UpdateDeviceCommand command);
}
