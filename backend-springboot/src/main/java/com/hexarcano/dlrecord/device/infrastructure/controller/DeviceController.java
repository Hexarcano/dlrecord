package com.hexarcano.dlrecord.device.infrastructure.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.hexarcano.dlrecord.device.application.service.DeviceService;
import com.hexarcano.dlrecord.device.domain.model.Device;
import com.hexarcano.dlrecord.device.infrastructure.controller.dto.CreateDeviceRequest;
import com.hexarcano.dlrecord.device.infrastructure.controller.dto.DeviceResponse;
import com.hexarcano.dlrecord.device.infrastructure.controller.dto.UpdateDeviceRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/devices")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;

    @PostMapping
    public ResponseEntity<DeviceResponse> createDevice(@RequestBody CreateDeviceRequest request) {
        Device device = deviceService.createDevice(request.toCreateDeviceCommand());

        DeviceResponse response = new DeviceResponse(
                device.getUuid(),
                device.getDeviceName(),
                device.getIpv4(),
                device.getIpv6(),
                device.getMacAddress(),
                device.getBrand().getName(),
                device.getDeviceModel().getName(),
                device.getDeviceType().getName(),
                device.getInventoryName());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<DeviceResponse> getDevice(@PathVariable String uuid) {
        return deviceService.findById(uuid)
                .map(device -> new DeviceResponse(
                        device.getUuid(),
                        device.getDeviceName(),
                        device.getIpv4(),
                        device.getIpv6(),
                        device.getMacAddress(),
                        device.getBrand().getName(),
                        device.getDeviceModel().getName(),
                        device.getDeviceType().getName(),
                        device.getInventoryName()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<DeviceResponse>> getAllDevices(Pageable pageable) {
        Page<DeviceResponse> page = deviceService.findAll(pageable)
                .map(device -> new DeviceResponse(
                        device.getUuid(),
                        device.getDeviceName(),
                        device.getIpv4(),
                        device.getIpv6(),
                        device.getMacAddress(),
                        device.getBrand().getName(),
                        device.getDeviceModel().getName(),
                        device.getDeviceType().getName(),
                        device.getInventoryName()));
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<DeviceResponse> updateDevice(
            @PathVariable String uuid,
            @RequestBody UpdateDeviceRequest request) {

        Device device = deviceService.updateDevice(request.toUpdateDeviceCommand(uuid));

        DeviceResponse response = new DeviceResponse(
                device.getUuid(),
                device.getDeviceName(),
                device.getIpv4(),
                device.getIpv6(),
                device.getMacAddress(),
                device.getBrand().getName(),
                device.getDeviceModel().getName(),
                device.getDeviceType().getName(),
                device.getInventoryName());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteDevice(@PathVariable String uuid) {
        return (deviceService.deleteDevice(uuid)) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
