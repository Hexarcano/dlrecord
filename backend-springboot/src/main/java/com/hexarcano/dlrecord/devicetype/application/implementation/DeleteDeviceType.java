package com.hexarcano.dlrecord.devicetype.application.implementation;

import com.hexarcano.dlrecord.devicetype.application.port.in.IDeleteDeviceType;
import com.hexarcano.dlrecord.devicetype.application.port.out.IDeviceTypeRepository;

import lombok.AllArgsConstructor;

/**
 * Use case implementation for deleting a device type.
 * 
 * <p>
 * This class contains the specific business logic for the deletion process.
 * It implements the {@link IDeleteDeviceType} input port and uses the
 * {@link IDeviceTypeRepository} output port to delete the device type data.
 * </p>
 */
@AllArgsConstructor
public class DeleteDeviceType implements IDeleteDeviceType {
    private final IDeviceTypeRepository repository;

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
