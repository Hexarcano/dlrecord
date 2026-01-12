package com.hexarcano.dlrecord.devicetype.application.port.in;

/**
 * Input port (Driving Port) for the 'Delete DeviceType' use case.
 * Defines the contract for deleting a device type from the application core.
 */
public interface IDeleteDeviceType {
    /**
     * Executes the logic to delete a device type by its unique identifier.
     * 
     * @param uuid The unique identifier of the device type to be deleted.
     * @return {@code true} if the deletion was successful, {@code false} otherwise.
     */
    boolean deleteDeviceType(String uuid);
}
