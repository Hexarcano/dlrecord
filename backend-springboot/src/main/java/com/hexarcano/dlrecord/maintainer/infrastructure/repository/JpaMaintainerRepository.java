package com.hexarcano.dlrecord.maintainer.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexarcano.dlrecord.maintainer.infrastructure.entities.MaintainerEntity;

@Repository
public interface JpaMaintainerRepository extends JpaRepository<MaintainerEntity, String> {
    Optional<MaintainerEntity> findByUsername(String username);

    long deleteByUuid(String uuid);
}
