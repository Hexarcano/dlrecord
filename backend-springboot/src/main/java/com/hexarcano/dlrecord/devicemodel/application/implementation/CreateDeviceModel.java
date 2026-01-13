package com.hexarcano.dlrecord.devicemodel.application.implementation;

import com.hexarcano.dlrecord.brand.application.port.out.IBrandRepository;
import com.hexarcano.dlrecord.brand.model.entity.Brand;
import com.hexarcano.dlrecord.devicemodel.application.port.in.ICreateDeviceModel;
import com.hexarcano.dlrecord.devicemodel.application.port.out.IDeviceModelRepository;
import com.hexarcano.dlrecord.devicemodel.model.entity.DeviceModel;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateDeviceModel implements ICreateDeviceModel {
    private final IDeviceModelRepository deviceModelRepository;
    private final IBrandRepository brandRepository;

    @Override
    public DeviceModel createDeviceModel(String name, String brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new IllegalArgumentException("Brand does not exist."));

        DeviceModel newDeviceModel = new DeviceModel(null, name, brand);

        return deviceModelRepository.save(newDeviceModel);
    }
}
