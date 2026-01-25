package com.hexarcano.dlrecord.devicemodel.application.implementation;

import com.hexarcano.dlrecord.brand.application.port.out.BrandRepositoryPort;
import com.hexarcano.dlrecord.brand.domain.model.Brand;
import com.hexarcano.dlrecord.devicemodel.application.port.in.CreateDeviceModelUseCase;
import com.hexarcano.dlrecord.devicemodel.application.port.in.command.CreateDeviceModelCommand;
import com.hexarcano.dlrecord.devicemodel.application.port.out.DeviceModelRepositoryPort;
import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;

import lombok.RequiredArgsConstructor;

/**
 * Use case implementation for creating a new device model.
 * 
 * <p>
 * This class contains the business logic for the creation process.
 * It implements the {@link CreateDeviceModelUseCase} input port and uses the
 * {@link DeviceModelRepositoryPort} output port to persist the device model
 * data.
 * </p>
 */
@RequiredArgsConstructor
public class CreateDeviceModel implements CreateDeviceModelUseCase {
    private final DeviceModelRepositoryPort deviceModelRepository;
    private final BrandRepositoryPort brandRepository;

    /**
     * Creates a new device model.
     * 
     * @param command The {@link CreateDeviceModelCommand} containing the data to
     *                create the device model.
     * @return The created device model with its assigned unique ID.
     * @throws IllegalArgumentException if the brand does not exist.
     */
    @Override
    public DeviceModel createDeviceModel(CreateDeviceModelCommand command) {
        Brand brand = brandRepository.findById(command.brandId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid brand reference."));

        if (deviceModelRepository.existsByName(command.name())) {
            throw new IllegalArgumentException("Device model already exists.");
        }

        DeviceModel deviceModel = new DeviceModel(null, command.name(), brand);

        return deviceModelRepository.save(deviceModel);
    }
}
