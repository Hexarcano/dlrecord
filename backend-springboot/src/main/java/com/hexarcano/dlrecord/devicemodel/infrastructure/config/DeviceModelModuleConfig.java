package com.hexarcano.dlrecord.devicemodel.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hexarcano.dlrecord.brand.application.port.out.IBrandRepository;
import com.hexarcano.dlrecord.brand.infrastructure.repository.JpaBrandRepository;
import com.hexarcano.dlrecord.devicemodel.application.implementation.CreateDeviceModel;
import com.hexarcano.dlrecord.devicemodel.application.implementation.DeleteDeviceModel;
import com.hexarcano.dlrecord.devicemodel.application.implementation.RetrieveDeviceModel;
import com.hexarcano.dlrecord.devicemodel.application.implementation.UpdateDeviceModel;
import com.hexarcano.dlrecord.devicemodel.application.port.out.IDeviceModelRepository;
import com.hexarcano.dlrecord.devicemodel.application.service.DeviceModelService;
import com.hexarcano.dlrecord.devicemodel.infrastructure.repository.JpaDeviceModelRepository;
import com.hexarcano.dlrecord.devicemodel.infrastructure.repository.JpaDeviceModelRepositoryAdapter;

@Configuration
public class DeviceModelModuleConfig {
    @Bean
    DeviceModelService deviceModelService(
            IDeviceModelRepository deviceModelRepository,
            IBrandRepository brandRepository) {
        return new DeviceModelService(
                new CreateDeviceModel(deviceModelRepository, brandRepository),
                new RetrieveDeviceModel(deviceModelRepository),
                new UpdateDeviceModel(deviceModelRepository, brandRepository),
                new DeleteDeviceModel(deviceModelRepository));
    }

    @Bean
    IDeviceModelRepository deviceModelRepository(
            JpaDeviceModelRepository repository,
            JpaBrandRepository jpaBrandRepository) {
        return new JpaDeviceModelRepositoryAdapter(repository, jpaBrandRepository);
    }
}
