package com.hexarcano.dlrecord.devicetype.application.implementation;

import org.springframework.stereotype.Service;

import com.hexarcano.dlrecord.devicetype.application.port.in.CreateDeviceTypeUseCase;
import com.hexarcano.dlrecord.devicetype.application.port.in.command.CreateDeviceTypeCommand;
import com.hexarcano.dlrecord.devicetype.application.port.out.DeviceTypeRepositoryPort;
import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;

import lombok.RequiredArgsConstructor;

/**
 * Use case implementation for creating a new device type.
 * 
 * <p>
 * This class contains the specific business logic for the creation process.
 * It implements the {@link CreateDeviceTypeUseCase} input port and uses the
 * {@link DeviceTypeRepositoryPort} output port to persist the device type data.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class CreateDeviceType implements CreateDeviceTypeUseCase {
    private final DeviceTypeRepositoryPort deviceTypeRepository;

    /**
     * Creates a new device type.
     * 
     * @param command The {@link CreateDeviceTypeCommand} containing data to create.
     * @return The created {@link DeviceType} domain model, typically with a new ID.
     */
    @Override
    public DeviceType createDeviceType(CreateDeviceTypeCommand command) {
        DeviceType deviceType = new DeviceType(null, command.name());

        return deviceTypeRepository.save(deviceType);
    }
}
