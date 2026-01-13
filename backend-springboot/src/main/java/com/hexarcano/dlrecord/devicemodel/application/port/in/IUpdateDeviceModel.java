package com.hexarcano.dlrecord.devicemodel.application.port.in;

import java.util.Optional;

import com.hexarcano.dlrecord.devicemodel.model.entity.DeviceModel;

public interface IUpdateDeviceModel {
    Optional<DeviceModel> updateDeviceModel(String uuid, String newName, String newBrandId);
}
