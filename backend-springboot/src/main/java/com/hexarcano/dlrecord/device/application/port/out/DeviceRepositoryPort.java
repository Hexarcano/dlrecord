package com.hexarcano.dlrecord.device.application.port.out;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hexarcano.dlrecord.brand.domain.model.Brand;
import com.hexarcano.dlrecord.device.domain.model.Device;
import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;
import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;

public interface DeviceRepositoryPort {
    Device save(Device device);

    Optional<Brand> findBrandById(String id);

    Optional<DeviceModel> findDeviceModelById(String id);

    Optional<DeviceType> findDeviceTypeById(String id);

    Optional<Device> findById(String uuid);

    Page<Device> findAll(Pageable pageable);

    boolean deleteById(String uuid);
}
