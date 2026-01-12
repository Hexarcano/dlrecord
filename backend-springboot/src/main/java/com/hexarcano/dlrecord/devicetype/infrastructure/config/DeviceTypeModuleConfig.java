package com.hexarcano.dlrecord.devicetype.infrastructure.config;

import com.hexarcano.dlrecord.devicetype.application.implementation.CreateDeviceType;
import com.hexarcano.dlrecord.devicetype.application.implementation.DeleteDeviceType;
import com.hexarcano.dlrecord.devicetype.application.implementation.RetrieveDeviceType;
import com.hexarcano.dlrecord.devicetype.application.implementation.UpdateDeviceType;
import com.hexarcano.dlrecord.devicetype.application.port.out.IDeviceTypeRepository;
import com.hexarcano.dlrecord.devicetype.application.service.DeviceTypeService;
import com.hexarcano.dlrecord.devicetype.infrastructure.repository.JpaDeviceTypeRepository;
import com.hexarcano.dlrecord.devicetype.infrastructure.repository.JpaDeviceTypeRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Configuration class for the DeviceType module.
 * <p>
 * This class is responsible for manually defining the dependency injection (DI) wiring
 * for the various components of the DeviceType module. By using @Bean methods, it tells Spring
 * how to construct and connect the services, repositories, and use cases.
 * </p>
 */
@Configuration
public class DeviceTypeModuleConfig {

    /**
     * Creates the {@link DeviceTypeService} bean.
     * <p>
     * This service acts as a facade, aggregating all the individual use case implementations
     * into a single entry point for the controller.
     * </p>
     * @param repository The repository port implementation, which will be injected by Spring.
     * @return An instance of {@link DeviceTypeService} with all its dependencies resolved.
     */
    @Bean
    DeviceTypeService deviceTypeService(IDeviceTypeRepository repository) {
        return new DeviceTypeService(
                new CreateDeviceType(repository),
                new RetrieveDeviceType(repository),
                new UpdateDeviceType(repository),
                new DeleteDeviceType(repository));
    }

    /**
     * Creates the {@link IDeviceTypeRepository} bean, providing the JPA implementation.
     * <p>
     * This method defines that whenever a component asks for an {@link IDeviceTypeRepository},
     * Spring should provide an instance of {@link JpaDeviceTypeRepositoryAdapter}. This is the
     * core of the Port-Adapter pattern, decoupling the application core from the specific
     * persistence technology.
     * </p>
     * @param repository The low-level Spring Data JpaRepository interface.
     * @return An instance of {@link JpaDeviceTypeRepositoryAdapter} that acts as the driven adapter.
     */
    @Bean
    IDeviceTypeRepository deviceTypeRepository(JpaDeviceTypeRepository repository) {
        return new JpaDeviceTypeRepositoryAdapter(repository);
    }
}
