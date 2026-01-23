package com.hexarcano.dlrecord.devicemodel.infrastructure.controller.dto;

import com.hexarcano.dlrecord.devicemodel.application.port.in.command.CreateDeviceModelCommand;

/**
 * DTO (Data Transfer Object) for creating a new Device Model.
 *
 * @param name    The name of the new device model.
 * @param brandId The unique identifier of the brand associated with this device
 *                model.
 */
public record CreateDeviceModelRequest(String name, String brandId) {
    public CreateDeviceModelCommand toCreateDeviceModelCommand() {
        return new CreateDeviceModelCommand(name, brandId);
    }
}
