package com.hexarcano.dlrecord.devicemodel.application.port.out;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.devicemodel.model.entity.DeviceModel;

public interface IDeviceModelRepository {
    DeviceModel save(DeviceModel deviceModel);

    Optional<DeviceModel> findById(String uuid);

    List<DeviceModel> findAll();

    boolean deleteById(String uuid);

    long countByBrandUuid(String brandUuid);
}
