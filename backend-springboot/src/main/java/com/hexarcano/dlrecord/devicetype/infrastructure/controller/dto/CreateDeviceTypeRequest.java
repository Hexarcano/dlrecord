package com.hexarcano.dlrecord.devicetype.infrastructure.controller.dto;

import com.hexarcano.dlrecord.devicetype.application.port.in.command.CreateDeviceTypeCommand;

public record CreateDeviceTypeRequest(String name) {
    public CreateDeviceTypeCommand toCreateDeviceTypeCommand() {
        return new CreateDeviceTypeCommand(name);
    }
}
