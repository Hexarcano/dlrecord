package com.hexarcano.dlrecord.maintainer.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hexarcano.dlrecord.maintainer.application.implementation.CreateMaintainer;
import com.hexarcano.dlrecord.maintainer.application.implementation.DeleteMaintainer;
import com.hexarcano.dlrecord.maintainer.application.implementation.RetrieveMaintainer;
import com.hexarcano.dlrecord.maintainer.application.implementation.UpdateMaintainer;
import com.hexarcano.dlrecord.maintainer.application.port.out.MaintainerRepositoryPort;
import com.hexarcano.dlrecord.maintainer.application.service.MaintainerService;
import com.hexarcano.dlrecord.maintainer.infrastructure.repository.JpaMaintainerRepository;
import com.hexarcano.dlrecord.maintainer.infrastructure.repository.JpaMaintainerRepositoryAdapter;

@Configuration
public class MaintainerModuleConfig {
    @Bean
    MaintainerService maintainerService(MaintainerRepositoryPort maintainerRepository) {
        return new MaintainerService(
                new CreateMaintainer(maintainerRepository),
                new RetrieveMaintainer(maintainerRepository),
                new UpdateMaintainer(maintainerRepository),
                new DeleteMaintainer(maintainerRepository));
    }

    @Bean
    MaintainerRepositoryPort maintainerRepository(JpaMaintainerRepository jpaMaintainerRepository) {
        return new JpaMaintainerRepositoryAdapter(jpaMaintainerRepository);
    }
}
