package com.hexarcano.dlrecord.device.infrastructure.controller.dto;

public record DeviceResponse(
        String uuid,
        String deviceName,
        String ipv4,
        String ipv6,
        String macAddress,
        String brandName,
        String modelName,
        String typeName,
        String inventoryName) {
}
