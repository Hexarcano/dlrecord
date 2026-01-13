package com.hexarcano.dlrecord.devicemodel.application.implementation;

import java.util.Optional;

import com.hexarcano.dlrecord.brand.application.port.out.IBrandRepository;
import com.hexarcano.dlrecord.brand.model.entity.Brand;
import com.hexarcano.dlrecord.devicemodel.application.port.in.IUpdateDeviceModel;
import com.hexarcano.dlrecord.devicemodel.application.port.out.IDeviceModelRepository;
import com.hexarcano.dlrecord.devicemodel.model.entity.DeviceModel;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateDeviceModel implements IUpdateDeviceModel {
    private final IDeviceModelRepository deviceModelRepository;
    private final IBrandRepository brandRepository;

    @Override
    public Optional<DeviceModel> updateDeviceModel(String uuid, String newName, String newBrandId) {
        return deviceModelRepository.findById(uuid).map(modelToUpdate -> {
            if (newName != null && !newName.equals(modelToUpdate.getName())) {
                modelToUpdate.changeName(newName);
            }

            if (newBrandId != null && !newBrandId.equals(modelToUpdate.getBrand().getUuid())) {
                Brand newBrand = brandRepository.findById(newBrandId)
                        .orElseThrow(
                                () -> new IllegalArgumentException("Brand does not exist."));

                modelToUpdate.changeBrand(newBrand);
            }

            return deviceModelRepository.save(modelToUpdate);
        });
    }
}
