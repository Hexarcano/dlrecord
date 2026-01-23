package com.hexarcano.dlrecord.devicemodel.infrastructure.controller.dto;

import com.hexarcano.dlrecord.devicemodel.application.port.in.command.UpdateDeviceModelCommand;

/**
 * DTO (Data Transfer Object) for updating an existing Device Model.
 *
 * @param name    The new name for the device model.
 * @param brandId The new brand ID for the device model.
 */
public record UpdateDeviceModelRequest(String name, String brandId) {
    public UpdateDeviceModelCommand toUpdateDeviceModelCommand() {
        return new UpdateDeviceModelCommand(name, brandId);
    }
}
