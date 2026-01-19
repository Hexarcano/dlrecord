package com.hexarcano.dlrecord.devicemodel.application.port.in;

/**
 * Input port (Use Case) for deleting a device model.
 */
public interface DeleteDeviceModelUseCase {
    /**
     * Deletes a device model by its unique identifier.
     * 
     * @param uuid The unique ID of the device model.
     * @return {@code true} if deleted, {@code false} otherwise.
     */
    boolean deleteDeviceModel(String uuid);
}
