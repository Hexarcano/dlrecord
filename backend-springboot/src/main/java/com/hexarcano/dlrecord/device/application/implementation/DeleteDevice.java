package com.hexarcano.dlrecord.device.application.implementation;

import com.hexarcano.dlrecord.device.application.port.in.DeleteDeviceUseCase;
import com.hexarcano.dlrecord.device.application.port.out.DeviceRepositoryPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteDevice implements DeleteDeviceUseCase {
    private final DeviceRepositoryPort repositoryPort;

    @Override
    public boolean deleteDevice(String uuid) {
        return repositoryPort.deleteById(uuid);
    }
}
