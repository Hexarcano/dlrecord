package com.hexarcano.dlrecord.devicemodel.application.implementation;

import com.hexarcano.dlrecord.devicemodel.application.port.in.IDeleteDeviceModel;
import com.hexarcano.dlrecord.devicemodel.application.port.out.IDeviceModelRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteDeviceModel implements IDeleteDeviceModel {
    private final IDeviceModelRepository repository;

    @Override
    public boolean deleteDeviceModel(String uuid) {
        return repository.deleteById(uuid);
    }
}
