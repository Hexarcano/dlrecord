package com.hexarcano.dlrecord.devicetype.infrastructure.controller.dto;

import com.hexarcano.dlrecord.devicetype.application.port.in.command.CreateDeviceTypeCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateDeviceTypeRequest(
        @NotBlank(message = "Name is required") @Size(max = 100, message = "Name must not exceed 100 characters") String name) {
    public CreateDeviceTypeCommand toCreateDeviceTypeCommand() {
        return new CreateDeviceTypeCommand(name);
    }
}
