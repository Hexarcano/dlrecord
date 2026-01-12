package com.hexarcano.dlrecord.brand.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hexarcano.dlrecord.brand.application.implementation.CreateBrand;
import com.hexarcano.dlrecord.brand.application.implementation.DeleteBrand;
import com.hexarcano.dlrecord.brand.application.implementation.RetrieveBrand;
import com.hexarcano.dlrecord.brand.application.implementation.UpdateBrand;
import com.hexarcano.dlrecord.brand.application.port.out.IBrandRepository;
import com.hexarcano.dlrecord.brand.application.service.BrandService;
import com.hexarcano.dlrecord.brand.infrastructure.repository.JpaBrandRepository;
import com.hexarcano.dlrecord.brand.infrastructure.repository.JpaBrandRepositoryAdapter;

/**
 * Spring Configuration class for the Brand module.
 * 
 * <p>
 * This class is responsible for manually defining the dependency injection (DI)
 * wiring for the various components of the Brand module.
 * </p>
 */
@Configuration
public class BrandModuleConfig {
    /**
     * Creates the {@link BrandService} bean.
     * 
     * <p>
     * This service acts as a facade, aggregating all the individual use case
     * implementations into a single entry point for the controller.
     * </p>
     * 
     * @param repository The repository port implementation, which will be injected
     *                   by Spring.
     * @return An instance of {@link BrandService} with all its dependencies
     *         resolved.
     */
    @Bean
    BrandService brandService(IBrandRepository repository) {
        return new BrandService(
                new CreateBrand(repository),
                new RetrieveBrand(repository),
                new UpdateBrand(repository),
                new DeleteBrand(repository));
    }

    /**
     * Creates the {@link IBrandRepository} bean, providing the JPA
     * implementation.
     * 
     * <p>
     * This method defines that whenever a component asks for an
     * {@link IBrandRepository}, Spring should provide an instance of
     * an adapter implementing it.
     * </p>
     * 
     * @param repository The low-level Spring Data JpaRepository interface.
     * @return An instance of {@link JpaBrandRepositoryAdapter} that acts as
     *         the driven adapter.
     */
    @Bean
    IBrandRepository brandRepository(JpaBrandRepository repository) {
        return new JpaBrandRepositoryAdapter(repository);
    }
}
