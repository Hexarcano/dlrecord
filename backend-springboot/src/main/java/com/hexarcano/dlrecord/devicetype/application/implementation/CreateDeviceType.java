package com.hexarcano.dlrecord.devicetype.application.implementation;

import com.hexarcano.dlrecord.devicetype.application.port.in.CreateDeviceTypeUseCase;
import com.hexarcano.dlrecord.devicetype.application.port.out.DeviceTypeRepositoryPort;
import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;

import lombok.AllArgsConstructor;

/**
 * Use case implementation for creating a new device type.
 * 
 * <p>
 * This class contains the specific business logic for the creation process.
 * It implements the {@link ICreateDeviceType} input port and uses the
 * {@link IDeviceTypeRepository} output port to persist the device type data.
 * </p>
 */
@AllArgsConstructor
public class CreateDeviceType implements CreateDeviceTypeUseCase {
    private final DeviceTypeRepositoryPort repository;

    /**
     * Creates a new device type.
     * 
     * @param deviceType The {@link DeviceType} domain model to create.
     * @return The created {@link DeviceType} domain model, typically with a new ID.
     */
    @Override
    public DeviceType createDeviceType(DeviceType deviceType) {
        return repository.save(deviceType);
    }
}
