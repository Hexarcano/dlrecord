package com.hexarcano.dlrecord.device.application.port.in.command;

public record UpdateDeviceCommand(
        String uuid,
        String deviceName,
        String ipv4,
        String ipv6,
        String macAddress,
        String brandId,
        String deviceModelId,
        String deviceTypeId,
        String inventoryName) {
}
