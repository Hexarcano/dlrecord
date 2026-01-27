package com.hexarcano.dlrecord.device.application.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hexarcano.dlrecord.device.application.port.in.CreateDeviceUseCase;
import com.hexarcano.dlrecord.device.application.port.in.DeleteDeviceUseCase;
import com.hexarcano.dlrecord.device.application.port.in.RetrieveDeviceUseCase;
import com.hexarcano.dlrecord.device.application.port.in.UpdateDeviceUseCase;
import com.hexarcano.dlrecord.device.application.port.in.command.CreateDeviceCommand;
import com.hexarcano.dlrecord.device.application.port.in.command.UpdateDeviceCommand;
import com.hexarcano.dlrecord.device.domain.model.Device;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeviceService
        implements CreateDeviceUseCase, RetrieveDeviceUseCase, UpdateDeviceUseCase, DeleteDeviceUseCase {

    private final CreateDeviceUseCase createDeviceUseCase;
    private final RetrieveDeviceUseCase retrieveDeviceUseCase;
    private final UpdateDeviceUseCase updateDeviceUseCase;
    private final DeleteDeviceUseCase deleteDeviceUseCase;

    @Override
    public Device createDevice(CreateDeviceCommand command) {
        return createDeviceUseCase.createDevice(command);
    }

    @Override
    public Optional<Device> findById(String uuid) {
        return retrieveDeviceUseCase.findById(uuid);
    }

    @Override
    public Page<Device> findAll(Pageable pageable) {
        return retrieveDeviceUseCase.findAll(pageable);
    }

    @Override
    public Device updateDevice(UpdateDeviceCommand command) {
        return updateDeviceUseCase.updateDevice(command);
    }

    @Override
    public boolean deleteDevice(String uuid) {
        return deleteDeviceUseCase.deleteDevice(uuid);
    }
}
