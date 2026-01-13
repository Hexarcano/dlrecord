package com.hexarcano.dlrecord.devicemodel.application.port.in;

import com.hexarcano.dlrecord.devicemodel.model.entity.DeviceModel;

/**
 * Input port for the device model creation use case.
 */
public interface ICreateDeviceModel {
    /**
     * Creates a new device model.
     *
     * @param name    The name of the device model.
     * @param brandId The unique identifier of the associated brand.
     * @return The created device model.
     */
    DeviceModel createDeviceModel(String name, String brandId);
}
