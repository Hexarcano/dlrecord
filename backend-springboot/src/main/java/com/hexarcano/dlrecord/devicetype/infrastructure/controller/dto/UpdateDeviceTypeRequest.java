package com.hexarcano.dlrecord.devicetype.infrastructure.controller.dto;

import com.hexarcano.dlrecord.devicetype.application.port.in.command.UpdateDeviceTypeCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateDeviceTypeRequest(
        @NotBlank(message = "Name is required") @Size(max = 100, message = "Name must not exceed 100 characters") String name) {
    public UpdateDeviceTypeCommand toUpdateDeviceTypeCommand() {
        return new UpdateDeviceTypeCommand(name);
    }
}
