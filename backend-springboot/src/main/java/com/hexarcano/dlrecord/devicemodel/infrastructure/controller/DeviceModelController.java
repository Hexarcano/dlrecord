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
import com.hexarcano.dlrecord.devicemodel.infrastructure.controller.dto.CreateDeviceModelRequest;
import com.hexarcano.dlrecord.devicemodel.infrastructure.controller.dto.UpdateDeviceModelRequest;
import com.hexarcano.dlrecord.devicemodel.model.entity.DeviceModel;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/device-models")
@AllArgsConstructor
public class DeviceModelController {
    private final DeviceModelService deviceModelService;

    @PostMapping()
    public ResponseEntity<DeviceModel> createDeviceModel(@RequestBody CreateDeviceModelRequest request) {
        DeviceModel createdDeviceModel = deviceModelService.createDeviceModel(request.name(), request.brandId());
        return new ResponseEntity<>(createdDeviceModel, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<DeviceModel>> getAllDeviceModels() {
        List<DeviceModel> list = deviceModelService.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceModel> findDeviceModelById(@PathVariable String id) {
        return deviceModelService.findById(id)
                .map(deviceModel -> new ResponseEntity<>(deviceModel, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceModel> updateDeviceModel(
            @PathVariable String id,
            @RequestBody UpdateDeviceModelRequest request) {
        return deviceModelService.updateDeviceModel(id, request.name(), request.brandId())
                .map(updatedDeviceModel -> new ResponseEntity<>(updatedDeviceModel, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeviceModel(@PathVariable String id) {
        return (deviceModelService.deleteDeviceModel(id))
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
