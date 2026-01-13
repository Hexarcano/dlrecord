package com.hexarcano.dlrecord.devicemodel.application.service;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.devicemodel.application.port.in.ICreateDeviceModel;
import com.hexarcano.dlrecord.devicemodel.application.port.in.IDeleteDeviceModel;
import com.hexarcano.dlrecord.devicemodel.application.port.in.IRetrieveDeviceModel;
import com.hexarcano.dlrecord.devicemodel.application.port.in.IUpdateDeviceModel;
import com.hexarcano.dlrecord.devicemodel.model.entity.DeviceModel;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeviceModelService
        implements ICreateDeviceModel, IRetrieveDeviceModel, IUpdateDeviceModel, IDeleteDeviceModel {
    private final ICreateDeviceModel createDeviceModel;
    private final IRetrieveDeviceModel retrieveDeviceModel;
    private final IUpdateDeviceModel updateDeviceModel;
    private final IDeleteDeviceModel deleteDeviceModel;

    @Override
    public DeviceModel createDeviceModel(String name, String brandId) {
        return createDeviceModel.createDeviceModel(name, brandId);
    }

    @Override
    public Optional<DeviceModel> findById(String uuid) {
        return retrieveDeviceModel.findById(uuid);
    }

    @Override
    public List<DeviceModel> findAll() {
        return retrieveDeviceModel.findAll();
    }

    @Override
    public Optional<DeviceModel> updateDeviceModel(String uuid, String newName, String newBrandId) {
        return updateDeviceModel.updateDeviceModel(uuid, newName, newBrandId);
    }

    @Override
    public boolean deleteDeviceModel(String uuid) {
        return deleteDeviceModel.deleteDeviceModel(uuid);
    }
}
