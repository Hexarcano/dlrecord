package com.hexarcano.dlrecord.device.application.implementation;

import com.hexarcano.dlrecord.brand.domain.model.Brand;
import com.hexarcano.dlrecord.device.application.port.in.CreateDeviceUseCase;
import com.hexarcano.dlrecord.device.application.port.in.command.CreateDeviceCommand;
import com.hexarcano.dlrecord.device.application.port.out.DeviceRepositoryPort;
import com.hexarcano.dlrecord.device.domain.exception.DeviceInvalidDataException;
import com.hexarcano.dlrecord.device.domain.model.Device;
import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;
import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateDevice implements CreateDeviceUseCase {
    private final DeviceRepositoryPort deviceRepository;

    @Override
    public Device createDevice(CreateDeviceCommand command) {
        Brand brand = deviceRepository.findBrandById(command.brandId())
                .orElseThrow(() -> new DeviceInvalidDataException("Brand not found"));

        DeviceModel model = deviceRepository.findDeviceModelById(command.deviceModelId())
                .orElseThrow(() -> new DeviceInvalidDataException("Device Model not found"));

        DeviceType type = deviceRepository.findDeviceTypeById(command.deviceTypeId())
                .orElseThrow(() -> new DeviceInvalidDataException("Device Type not found"));

        Device device = new Device(
                null,
                command.deviceName(),
                command.ipv4(),
                command.ipv6(),
                command.macAddress(),
                brand,
                model,
                type,
                command.inventoryName());

        return deviceRepository.save(device);
    }
}
