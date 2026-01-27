package com.hexarcano.dlrecord.device.infrastructure.controller.dto;

import com.hexarcano.dlrecord.device.application.port.in.command.UpdateDeviceCommand;

public record UpdateDeviceRequest(
        String deviceName,
        String ipv4,
        String ipv6,
        String macAddress,
        String brandId,
        String deviceModelId,
        String deviceTypeId,
        String inventoryName) {

    public UpdateDeviceCommand toUpdateDeviceCommand(String uuid) {
        return new UpdateDeviceCommand(
                uuid,
                deviceName,
                ipv4,
                ipv6,
                macAddress,
                brandId,
                deviceModelId,
                deviceTypeId,
                inventoryName);
    }
}
