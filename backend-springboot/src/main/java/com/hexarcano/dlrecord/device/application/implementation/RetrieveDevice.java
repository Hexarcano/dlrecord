package com.hexarcano.dlrecord.device.application.implementation;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hexarcano.dlrecord.device.application.port.in.RetrieveDeviceUseCase;
import com.hexarcano.dlrecord.device.application.port.out.DeviceRepositoryPort;
import com.hexarcano.dlrecord.device.domain.model.Device;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RetrieveDevice implements RetrieveDeviceUseCase {
    private final DeviceRepositoryPort repositoryPort;

    @Override
    public Optional<Device> findById(String uuid) {
        return repositoryPort.findById(uuid);
    }

    @Override
    public Page<Device> findAll(Pageable pageable) {
        return repositoryPort.findAll(pageable);
    }
}
