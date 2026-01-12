package com.hexarcano.dlrecord.devicetype.infrastructure.controller;

import com.hexarcano.dlrecord.devicetype.application.service.DeviceTypeService;
import com.hexarcano.dlrecord.devicetype.model.entity.DeviceType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Primary (Driving) Adapter that exposes device type use cases via a RESTful API.
 * It handles incoming HTTP requests and delegates them to the application service layer.
 */
@RestController
@RequestMapping("/api/v1/device-types")
@AllArgsConstructor
public class DeviceTypeController {
    private final DeviceTypeService deviceTypeService;

    /**
     * REST endpoint to create a new device type.
     * The incoming request body is deserialized into a {@link DeviceType} object.
     * The domain object's constructor validates the data. If invalid, a global exception
     * handler returns a 400 Bad Request.
     *
     * @param deviceType The device type data sent in the request body.
     * @return A {@link ResponseEntity} with the created device type and an HTTP status of 201 (Created).
     */
    @PostMapping()
    public ResponseEntity<DeviceType> createDeviceType(@RequestBody DeviceType deviceType) {
        DeviceType createdDeviceType = deviceTypeService.createDeviceType(deviceType);
        return new ResponseEntity<>(createdDeviceType, HttpStatus.CREATED);
    }

    /**
     * REST endpoint to retrieve all device types.
     * @return A {@link ResponseEntity} with a list of all device types and an HTTP status of 200 (OK).
     */
    @GetMapping()
    public ResponseEntity<List<DeviceType>> getAllDeviceTypes() {
        List<DeviceType> list = deviceTypeService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * REST endpoint to find a single device type by its unique ID.
     * @param id The unique identifier of the device type, passed in the URL path.
     * @return A {@link ResponseEntity} with the found device type and HTTP status 200 (OK),
     *         or HTTP status 404 (Not Found) if the device type does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DeviceType> findDeviceTypeById(@PathVariable String id) {
        return deviceTypeService.findById(id)
                .map(deviceType -> new ResponseEntity<>(deviceType, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * REST endpoint to update an existing device type.
     * @param id The unique identifier of the device type to update, passed in the URL path.
     * @param deviceType The updated device type data sent in the request body.
     * @return A {@link ResponseEntity} with the updated device type and HTTP status 200 (OK),
     *         or HTTP status 404 (Not Found) if the device type to update does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DeviceType> updateDeviceType(@PathVariable String id, @RequestBody DeviceType deviceType) {
        return deviceTypeService.updateDeviceType(id, deviceType)
                .map(updatedDeviceType -> new ResponseEntity<>(updatedDeviceType, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * REST endpoint to delete a device type by its unique ID.
     * @param id The unique identifier of the device type to delete, passed in the URL path.
     * @return A {@link ResponseEntity} with HTTP status 204 (No Content) if deletion was successful,
     *         or HTTP status 404 (Not Found) if the device type does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeviceType(@PathVariable String id) {
        return (deviceTypeService.deleteDeviceType(id))
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
