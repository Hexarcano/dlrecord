package com.hexarcano.dlrecord.devicemodel.application.port.in;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.devicemodel.model.entity.DeviceModel;

public interface IRetrieveDeviceModel {
    Optional<DeviceModel> findById(String uuid);

    List<DeviceModel> findAll();
}
