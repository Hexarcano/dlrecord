package com.hexarcano.dlrecord.devicemodel.application.port.in;

/**
 * Input port for the device model deletion use case.
 */
public interface IDeleteDeviceModel {
    /**
     * Deletes an existing device model.
     *
     * @param uuid The unique identifier of the device model to delete.
     * @return true if the deletion was successful, false if the model was not found.
     */
    boolean deleteDeviceModel(String uuid);
}
