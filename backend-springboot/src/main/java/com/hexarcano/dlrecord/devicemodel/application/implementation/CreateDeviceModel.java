package com.hexarcano.dlrecord.devicemodel.application.implementation;

import com.hexarcano.dlrecord.brand.application.port.out.BrandRepositoryPort;
import com.hexarcano.dlrecord.devicemodel.application.port.in.CreateDeviceModelUseCase;
import com.hexarcano.dlrecord.devicemodel.application.port.out.DeviceModelRepositoryPort;
import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;

import lombok.RequiredArgsConstructor;

/**
 * Use case implementation for creating a new device model.
 * 
 * <p>
 * This class contains the business logic for the creation process.
 * It implements the {@link ICreateDeviceModel} input port and uses the
 * {@link IDeviceModelRepository} output port to persist the device model data.
 * </p>
 */
@RequiredArgsConstructor
public class CreateDeviceModel implements CreateDeviceModelUseCase {
    private final DeviceModelRepositoryPort deviceModelRepository;
    private final BrandRepositoryPort brandRepository;

    /**
     * Creates a new device model.
     * 
     * @param deviceModel The device model to create.
     * @return The created device model with its assigned unique ID.
     * @throws IllegalArgumentException if the brand does not exist.
     */
    @Override
    public DeviceModel createDeviceModel(DeviceModel deviceModel) {
        if (!brandRepository.existsById(deviceModel.getBrand().getUuid())) {
            throw new IllegalArgumentException("Invalid brand reference.");
        }

        if (deviceModelRepository.existsByName(deviceModel.getName())) {
            throw new IllegalArgumentException("Device model already exists.");
        }

        return deviceModelRepository.save(deviceModel);
    }
}
