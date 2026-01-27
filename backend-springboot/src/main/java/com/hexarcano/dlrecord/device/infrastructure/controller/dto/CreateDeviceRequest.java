package com.hexarcano.dlrecord.device.infrastructure.controller.dto;

import com.hexarcano.dlrecord.device.application.port.in.command.CreateDeviceCommand;

public record CreateDeviceRequest(
        String deviceName,
        String ipv4,
        String ipv6,
        String macAddress,
        String brandId,
        String deviceModelId,
        String deviceTypeId,
        String inventoryName) {

    public CreateDeviceCommand toCreateDeviceCommand() {
        return new CreateDeviceCommand(
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
