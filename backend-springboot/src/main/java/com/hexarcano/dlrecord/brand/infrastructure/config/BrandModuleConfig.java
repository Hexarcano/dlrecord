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

@Configuration
public class BrandModuleConfig {
    @Bean
    BrandService brandService(IBrandRepository repository) {
        return new BrandService(
                new CreateBrand(repository),
                new RetrieveBrand(repository),
                new UpdateBrand(repository),
                new DeleteBrand(repository));
    }

    @Bean
    IBrandRepository brandRepository(JpaBrandRepository repository) {
        return new JpaBrandRepositoryAdapter(repository);
    }
}
