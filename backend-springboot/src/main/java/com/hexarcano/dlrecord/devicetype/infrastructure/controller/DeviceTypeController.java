package com.hexarcano.dlrecord.devicetype.infrastructure.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hexarcano.dlrecord.devicetype.application.service.DeviceTypeService;
import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;
import com.hexarcano.dlrecord.devicetype.infrastructure.controller.dto.CreateDeviceTypeRequest;
import com.hexarcano.dlrecord.devicetype.infrastructure.controller.dto.UpdateDeviceTypeRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Primary (Driving) Adapter that exposes device type use cases via a RESTful
 * API.
 * It handles incoming HTTP requests and delegates them to the application
 * service layer.
 */
@RestController
@RequestMapping("/api/v1/device-types")
@RequiredArgsConstructor
public class DeviceTypeController {
    private final DeviceTypeService deviceTypeService;

    /**
     * REST endpoint to create a new device type.
     *
     * @param deviceType The device type data sent in the request body.
     * @return A {@link ResponseEntity} with the created device type and an HTTP
     *         status of 201 (Created).
     */
    @PostMapping()
    public ResponseEntity<DeviceType> createDeviceType(@Valid @RequestBody CreateDeviceTypeRequest request) {
        DeviceType createdDeviceType = deviceTypeService.createDeviceType(request.toCreateDeviceTypeCommand());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDeviceType.getUuid())
                .toUri();

        return ResponseEntity.created(location).body(createdDeviceType);
    }

    /**
     * REST endpoint to retrieve all device types with pagination.
     * 
     * @param pageable The pagination information.
     * @return A {@link ResponseEntity} with a page of all device types and an HTTP
     *         status of 200 (OK).
     */
    @GetMapping()
    public ResponseEntity<Page<DeviceType>> getAllDeviceTypes(Pageable pageable) {
        Page<DeviceType> page = deviceTypeService.findAll(pageable);

        return ResponseEntity.ok(page);
    }

    /**
     * REST endpoint to find a single device type by its unique ID.
     * 
     * @param id The unique identifier of the device type, passed in the URL path.
     * @return A {@link ResponseEntity} with the found device type and HTTP status
     *         200 (OK), or HTTP status 404 (Not Found) if the device type does not
     *         exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DeviceType> findDeviceTypeById(@PathVariable("id") String id) {
        return deviceTypeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * REST endpoint to update an existing device type.
     * 
     * @param id      The unique identifier of the device type to update, passed in
     *                the URL path.
     * @param request The updated device type data sent in the request body.
     * @return A {@link ResponseEntity} with the updated device type and HTTP status
     *         200 (OK),or HTTP status 404 (Not Found) if the device type to update
     *         does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DeviceType> updateDeviceType(@PathVariable("id") String id,
            @Valid @RequestBody UpdateDeviceTypeRequest request) {
        return deviceTypeService.updateDeviceType(id, request.toUpdateDeviceTypeCommand())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * REST endpoint to delete a device type by its unique ID.
     * 
     * @param id The unique identifier of the device type to delete, passed in the
     *           URL path.
     * @return A {@link ResponseEntity} with HTTP status 204 (No Content) if
     *         deletion was successful, or HTTP status 404 (Not Found) if the device
     *         type does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeviceType(@PathVariable("id") String id) {
        return (deviceTypeService.deleteDeviceType(id))
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
