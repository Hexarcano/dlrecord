package com.hexarcano.dlrecord.devicemodel.infrastructure.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexarcano.dlrecord.devicemodel.application.service.DeviceModelService;
import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;
import com.hexarcano.dlrecord.devicemodel.infrastructure.controller.dto.CreateDeviceModelRequest;
import com.hexarcano.dlrecord.devicemodel.infrastructure.controller.dto.UpdateDeviceModelRequest;

import lombok.RequiredArgsConstructor;

/**
 * Primary (Driving) Adapter that exposes device model use cases via a RESTful
 * API.
 * It handles incoming HTTP requests and delegates them to the application
 * service layer.
 */
@RestController
@RequestMapping("/api/v1/device-models")
@RequiredArgsConstructor
public class DeviceModelController {
    private final DeviceModelService deviceModelService;

    /**
     * REST endpoint to create a new device model.
     *
     * @param request The device model data sent in the request body.
     * @return A {@link ResponseEntity} with the created device model and an HTTP
     *         status of 201 (Created).
     */
    @PostMapping()
    public ResponseEntity<DeviceModel> createDeviceModel(@RequestBody CreateDeviceModelRequest request) {
        DeviceModel createdDeviceModel = deviceModelService.createDeviceModel(request.toCreateDeviceModelCommand());

        return new ResponseEntity<>(createdDeviceModel, HttpStatus.CREATED);
    }

    /**
     * REST endpoint to retrieve all device models.
     *
     * @return A {@link ResponseEntity} with a list of all device models and an HTTP
     *         status of 200 (OK).
     */
    @GetMapping()
    public ResponseEntity<List<DeviceModel>> getAllDeviceModels() {
        List<DeviceModel> list = deviceModelService.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * REST endpoint to find a single device model by its unique ID.
     *
     * @param id The unique identifier of the device model, passed in the URL path.
     * @return A {@link ResponseEntity} with the found device model and HTTP status
     *         200 (OK), or HTTP status 404 (Not Found) if the device model does not
     *         exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DeviceModel> findDeviceModelById(@PathVariable String id) {
        return deviceModelService.findById(id)
                .map(deviceModel -> new ResponseEntity<>(deviceModel, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * REST endpoint to update an existing device model.
     *
     * @param id      The unique identifier of the device model to update, passed in
     *                the URL path.
     * @param request The updated device model data sent in the request body.
     * @return A {@link ResponseEntity} with the updated device model and HTTP
     *         status
     *         200 (OK), or HTTP status 404 (Not Found) if the device model to
     *         update
     *         does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DeviceModel> updateDeviceModel(
            @PathVariable String id,
            @RequestBody UpdateDeviceModelRequest request) {
        return deviceModelService.updateDeviceModel(id, request.toUpdateDeviceModelCommand())
                .map(updatedDeviceModel -> new ResponseEntity<>(updatedDeviceModel, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * REST endpoint to delete a device model by its unique ID.
     *
     * @param id The unique identifier of the device model to delete, passed in the
     *           URL path.
     * @return A {@link ResponseEntity} with HTTP status 204 (No Content) if
     *         deletion was successful, or HTTP status 404 (Not Found) if the device
     *         model does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeviceModel(@PathVariable String id) {
        return (deviceModelService.deleteDeviceModel(id))
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
