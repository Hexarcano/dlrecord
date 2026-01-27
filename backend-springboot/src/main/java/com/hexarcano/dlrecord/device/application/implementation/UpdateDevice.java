package com.hexarcano.dlrecord.device.application.implementation;

import com.hexarcano.dlrecord.brand.domain.model.Brand;
import com.hexarcano.dlrecord.device.application.port.in.UpdateDeviceUseCase;
import com.hexarcano.dlrecord.device.application.port.in.command.UpdateDeviceCommand;
import com.hexarcano.dlrecord.device.application.port.out.DeviceRepositoryPort;
import com.hexarcano.dlrecord.device.domain.exception.DeviceInvalidDataException;
import com.hexarcano.dlrecord.device.domain.model.Device;
import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;
import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateDevice implements UpdateDeviceUseCase {
    private final DeviceRepositoryPort repositoryPort;

    @Override
    public Device updateDevice(UpdateDeviceCommand command) {
        Device existingDevice = repositoryPort.findById(command.uuid())
                .orElseThrow(() -> new DeviceInvalidDataException("Device not found"));

        Device device = existingDevice;

        if (command.deviceName() != null) {
            device.changeDeviceName(command.deviceName());
        }
        if (command.ipv4() != null) {
            device.changeIpv4(command.ipv4());
        }
        if (command.ipv6() != null) {
            device.changeIpv6(command.ipv6());
        }
        if (command.macAddress() != null) {
            device.changeMacAddress(command.macAddress());
        }

        if (command.brandId() != null && !command.brandId().equals(existingDevice.getBrand().getUuid())) {
            Brand brand = repositoryPort.findBrandById(command.brandId())
                    .orElseThrow(() -> new DeviceInvalidDataException("Brand not found"));
            device.changeBrand(brand);
        }

        if (command.deviceModelId() != null
                && !command.deviceModelId().equals(existingDevice.getDeviceModel().getUuid())) {
            DeviceModel model = repositoryPort.findDeviceModelById(command.deviceModelId())
                    .orElseThrow(() -> new DeviceInvalidDataException("Device Model not found"));
            device.changeDeviceModel(model);
        }

        if (command.deviceTypeId() != null
                && !command.deviceTypeId().equals(existingDevice.getDeviceType().getUuid())) {
            DeviceType type = repositoryPort.findDeviceTypeById(command.deviceTypeId())
                    .orElseThrow(() -> new DeviceInvalidDataException("Device Type not found"));
            device.changeDeviceType(type);
        }

        if (command.inventoryName() != null) {
            device.changeInventoryName(command.inventoryName());
        }

        return repositoryPort.save(device);
    }
}
