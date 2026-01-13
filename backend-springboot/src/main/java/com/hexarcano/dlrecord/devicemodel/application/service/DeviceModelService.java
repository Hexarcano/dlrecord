package com.hexarcano.dlrecord.devicemodel.application.service;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.devicemodel.application.port.in.ICreateDeviceModel;
import com.hexarcano.dlrecord.devicemodel.application.port.in.IDeleteDeviceModel;
import com.hexarcano.dlrecord.devicemodel.application.port.in.IRetrieveDeviceModel;
import com.hexarcano.dlrecord.devicemodel.application.port.in.IUpdateDeviceModel;
import com.hexarcano.dlrecord.devicemodel.model.entity.DeviceModel;

import lombok.AllArgsConstructor;

/**
 * A service facade that aggregates all device-model-related use cases into a single
 * entry point.
 * 
 * <p>
 * This class implements all the input port interfaces
 * ({@link ICreateDeviceModel}, {@link IRetrieveDeviceModel}, etc.) and delegates
 * the calls to the actual use case implementations. It is used by the primary
 * adapter (e.g., {@code DeviceModelController}) to interact with the
 * application's core logic.
 * </p>
 */
@AllArgsConstructor
public class DeviceModelService
        implements ICreateDeviceModel, IRetrieveDeviceModel, IUpdateDeviceModel, IDeleteDeviceModel {
    private final ICreateDeviceModel createDeviceModel;
    private final IRetrieveDeviceModel retrieveDeviceModel;
    private final IUpdateDeviceModel updateDeviceModel;
    private final IDeleteDeviceModel deleteDeviceModel;

    /**
     * Delegates the call to the 'Create DeviceModel' use case implementation.
     * 
     * @param name    The name of the device model.
     * @param brandId The ID of the brand associated with the device model.
     * @return The created device model.
     */
    @Override
    public DeviceModel createDeviceModel(String name, String brandId) {
        return createDeviceModel.createDeviceModel(name, brandId);
    }

    /**
     * Delegates the call to the 'Retrieve DeviceModel' use case to find a device
     * model by its ID.
     * 
     * @param uuid The unique ID of the device model to find.
     * @return An {@link Optional} with the found device model, or empty.
     */
    @Override
    public Optional<DeviceModel> findById(String uuid) {
        return retrieveDeviceModel.findById(uuid);
    }

    /**
     * Delegates the call to the 'Retrieve DeviceModel' use case to find all device
     * models.
     * 
     * @return A list of all device models.
     */
    @Override
    public List<DeviceModel> findAll() {
        return retrieveDeviceModel.findAll();
    }

    /**
     * Delegates the call to the 'Update DeviceModel' use case.
     * 
     * @param uuid       The unique ID of the device model to update.
     * @param newName    The new name for the device model.
     * @param newBrandId The new brand ID for the device model.
     * @return An {@link Optional} with the updated device model, or empty.
     */
    @Override
    public Optional<DeviceModel> updateDeviceModel(String uuid, String newName, String newBrandId) {
        return updateDeviceModel.updateDeviceModel(uuid, newName, newBrandId);
    }

    /**
     * Delegates the call to the 'Delete DeviceModel' use case.
     * 
     * @param uuid The unique ID of the device model to delete.
     * @return {@code true} if deleted, {@code false} otherwise.
     */
    @Override
    public boolean deleteDeviceModel(String uuid) {
        return deleteDeviceModel.deleteDeviceModel(uuid);
    }
}
