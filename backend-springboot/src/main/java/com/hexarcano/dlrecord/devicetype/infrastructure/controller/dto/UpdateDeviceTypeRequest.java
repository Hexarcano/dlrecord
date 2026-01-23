package com.hexarcano.dlrecord.devicetype.infrastructure.controller.dto;

import com.hexarcano.dlrecord.devicetype.application.port.in.command.UpdateDeviceTypeCommand;

public record UpdateDeviceTypeRequest(String name) {
    public UpdateDeviceTypeCommand toUpdateDeviceTypeCommand() {
        return new UpdateDeviceTypeCommand(name);
    }
}
