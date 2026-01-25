package com.hexarcano.dlrecord.devicetype.application.implementation;

import com.hexarcano.dlrecord.devicetype.application.port.in.DeleteDeviceTypeUseCase;
import com.hexarcano.dlrecord.devicetype.application.port.out.DeviceTypeRepositoryPort;

import lombok.RequiredArgsConstructor;

/**
 * Use case implementation for deleting a device type.
 * 
 * <p>
 * This class contains the specific business logic for the deletion process.
 * It implements the {@link DeleteDeviceTypeUseCase} input port and uses the
 * {@link DeviceTypeRepositoryPort} output port to delete the device type data.
 * </p>
 */
@RequiredArgsConstructor
public class DeleteDeviceType implements DeleteDeviceTypeUseCase {
    private final DeviceTypeRepositoryPort repository;

    /**
     * Deletes a device type by its unique identifier.
     * 
     * @param uuid The unique ID of the device type to be deleted.
     * @return {@code true} if the device type was successfully deleted,
     *         {@code false} otherwise.
     */
    @Override
    public boolean deleteDeviceType(String uuid) {
        return repository.deleteById(uuid);
    }
}
