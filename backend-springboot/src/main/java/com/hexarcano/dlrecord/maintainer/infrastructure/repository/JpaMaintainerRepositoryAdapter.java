package com.hexarcano.dlrecord.maintainer.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.hexarcano.dlrecord.maintainer.application.port.out.MaintainerRepositoryPort;
import com.hexarcano.dlrecord.maintainer.infrastructure.entities.MaintainerEntity;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaMaintainerRepositoryAdapter implements MaintainerRepositoryPort {
    private final JpaMaintainerRepository repository;

    @Override
    @Transactional
    public Maintainer save(Maintainer maintainer) {
        MaintainerEntity entity = MaintainerEntity.fromDomainModel(maintainer);

        return repository.save(entity).toDomainModel();
    }

    @Override
    @Transactional
    public Optional<Maintainer> findByUsername(String username) {
        return repository.findByUsername(username).map(MaintainerEntity::toDomainModel);
    }

    @Override
    @Transactional
    public Optional<Maintainer> findById(String uuid) {
        return repository.findById(uuid).map(MaintainerEntity::toDomainModel);
    }

    @Override
    @Transactional
    public List<Maintainer> findAll() {
        return repository.findAll().stream()
                .map(MaintainerEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean delete(String uuid) {
        long affectedRows = repository.deleteByUuid(uuid);

        return affectedRows > 0;
    }

}
