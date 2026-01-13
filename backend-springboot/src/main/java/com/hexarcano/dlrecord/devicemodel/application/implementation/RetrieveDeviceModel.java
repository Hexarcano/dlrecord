package com.hexarcano.dlrecord.devicemodel.application.implementation;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.devicemodel.application.port.in.IRetrieveDeviceModel;
import com.hexarcano.dlrecord.devicemodel.application.port.out.IDeviceModelRepository;
import com.hexarcano.dlrecord.devicemodel.model.entity.DeviceModel;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RetrieveDeviceModel implements IRetrieveDeviceModel {
    private final IDeviceModelRepository repository;

    @Override
    public Optional<DeviceModel> findById(String uuid) {
        return repository.findById(uuid);
    }

    @Override
    public List<DeviceModel> findAll() {
        return repository.findAll();
    }
}
