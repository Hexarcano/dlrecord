package com.hexarcano.dlrecord.devicemodel.application.service;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.devicemodel.application.port.in.CreateDeviceModelUseCase;
import com.hexarcano.dlrecord.devicemodel.application.port.in.DeleteDeviceModelUseCase;
import com.hexarcano.dlrecord.devicemodel.application.port.in.RetrieveDeviceModelUseCase;
import com.hexarcano.dlrecord.devicemodel.application.port.in.UpdateDeviceModelUseCase;
import com.hexarcano.dlrecord.devicemodel.application.port.in.command.CreateDeviceModelCommand;
import com.hexarcano.dlrecord.devicemodel.application.port.in.command.UpdateDeviceModelCommand;
import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;

import lombok.RequiredArgsConstructor;

/**
 * A service facade that aggregates all device-model-related use cases into a
 * single
 * entry point.
 * 
 * <p>
 * This class implements all the input port interfaces and delegates the calls
 * to the actual use case implementations. It is used by the primary adapter
 * (e.g., DeviceModelController) to interact with the application's core logic.
 * </p>
 */
@RequiredArgsConstructor
public class DeviceModelService
        implements CreateDeviceModelUseCase, RetrieveDeviceModelUseCase, UpdateDeviceModelUseCase,
        DeleteDeviceModelUseCase {
    private final CreateDeviceModelUseCase createDeviceModel;
    private final RetrieveDeviceModelUseCase retrieveDeviceModel;
    private final UpdateDeviceModelUseCase updateDeviceModel;
    private final DeleteDeviceModelUseCase deleteDeviceModel;

    /**
     * Delegates the call to the 'Create DeviceModel' use case implementation.
     * 
     * @param deviceModel The device model to create.
     * @return The created device model.
     */
    @Override
    public DeviceModel createDeviceModel(CreateDeviceModelCommand command) {
        return createDeviceModel.createDeviceModel(command);
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
    public Optional<DeviceModel> updateDeviceModel(String uuid, UpdateDeviceModelCommand command) {
        return updateDeviceModel.updateDeviceModel(uuid, command);
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
