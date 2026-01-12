package com.hexarcano.dlrecord.devicetype.application.implementation;

import com.hexarcano.dlrecord.devicetype.application.port.in.ICreateDeviceType;
import com.hexarcano.dlrecord.devicetype.application.port.out.IDeviceTypeRepository;
import com.hexarcano.dlrecord.devicetype.model.entity.DeviceType;
import lombok.AllArgsConstructor;

/**
 * Use case implementation for creating a new device type.
 * <p>
 * This class contains the specific business logic for the creation process.
 * It implements the {@link ICreateDeviceType} input port and uses the {@link IDeviceTypeRepository}
 * output port to persist the device type data.
 * </p>
 */
@AllArgsConstructor
public class CreateDeviceType implements ICreateDeviceType {
    private final IDeviceTypeRepository repository;

    /**
     * Creates a new device type by persisting it through the repository port.
     * @param deviceType The {@link DeviceType} domain model to create.
     * @return The created {@link DeviceType} domain model, typically with a new ID.
     */
    @Override
    public DeviceType createDeviceType(DeviceType deviceType) {
        // In a more complex scenario, additional business logic (e.g., sending a notification) could be added here.
        return repository.save(deviceType);
    }
}
