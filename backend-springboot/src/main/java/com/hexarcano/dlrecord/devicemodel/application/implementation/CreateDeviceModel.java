package com.hexarcano.dlrecord.devicemodel.application.implementation;

import com.hexarcano.dlrecord.brand.application.port.out.BrandRepositoryPort;
import com.hexarcano.dlrecord.brand.domain.model.Brand;
import com.hexarcano.dlrecord.devicemodel.application.port.in.ICreateDeviceModel;
import com.hexarcano.dlrecord.devicemodel.application.port.out.IDeviceModelRepository;
import com.hexarcano.dlrecord.devicemodel.model.entity.DeviceModel;

import lombok.AllArgsConstructor;

/**
 * Use case implementation for creating a new device model.
 * 
 * <p>
 * This class contains the business logic for the creation process.
 * It implements the {@link ICreateDeviceModel} input port and uses the
 * {@link IDeviceModelRepository} output port to persist the device model data.
 * </p>
 */
@AllArgsConstructor
public class CreateDeviceModel implements ICreateDeviceModel {
    private final IDeviceModelRepository deviceModelRepository;
    private final BrandRepositoryPort brandRepository;

    /**
     * Creates a new device model.
     * 
     * @param name    The name of the new device model.
     * @param brandId The unique identifier of the associated brand.
     * @return The created {@link DeviceModel} domain model.
     * @throws IllegalArgumentException if the brand does not exist.
     */
    @Override
    public DeviceModel createDeviceModel(String name, String brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new IllegalArgumentException("Brand does not exist."));

        DeviceModel newDeviceModel = new DeviceModel(null, name, brand);

        return deviceModelRepository.save(newDeviceModel);
    }
}
