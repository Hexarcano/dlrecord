package com.hexarcano.dlrecord.device.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hexarcano.dlrecord.brand.infrastructure.repository.JpaBrandRepository;
import com.hexarcano.dlrecord.device.application.implementation.CreateDevice;
import com.hexarcano.dlrecord.device.application.implementation.DeleteDevice;
import com.hexarcano.dlrecord.device.application.implementation.RetrieveDevice;
import com.hexarcano.dlrecord.device.application.implementation.UpdateDevice;
import com.hexarcano.dlrecord.device.application.port.out.DeviceRepositoryPort;
import com.hexarcano.dlrecord.device.application.service.DeviceService;
import com.hexarcano.dlrecord.device.infrastructure.adapter.JpaDeviceRepositoryAdapter;
import com.hexarcano.dlrecord.device.infrastructure.repository.JpaDeviceRepository;
import com.hexarcano.dlrecord.devicemodel.infrastructure.repository.JpaDeviceModelRepository;
import com.hexarcano.dlrecord.devicetype.infrastructure.repository.JpaDeviceTypeRepository;

@Configuration
public class DeviceModuleConfig {
    @Bean
    public DeviceService deviceService(DeviceRepositoryPort deviceRepositoryPort) {
        return new DeviceService(
                new CreateDevice(deviceRepositoryPort),
                new RetrieveDevice(deviceRepositoryPort),
                new UpdateDevice(deviceRepositoryPort),
                new DeleteDevice(deviceRepositoryPort));
    }

    @Bean
    public DeviceRepositoryPort deviceRepositoryPort(
            JpaDeviceRepository deviceRepository,
            JpaBrandRepository brandRepository,
            JpaDeviceModelRepository deviceModelRepository,
            JpaDeviceTypeRepository deviceTypeRepository) {
        return new JpaDeviceRepositoryAdapter(
                deviceRepository,
                brandRepository,
                deviceModelRepository,
                deviceTypeRepository);
    }
}
