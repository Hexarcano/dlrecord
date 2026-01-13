package com.hexarcano.dlrecord.devicemodel.application.port.in;

import com.hexarcano.dlrecord.devicemodel.model.entity.DeviceModel;

public interface ICreateDeviceModel {
    DeviceModel createDeviceModel(String name, String brandId);
}
