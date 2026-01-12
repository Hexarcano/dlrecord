package com.hexarcano.dlrecord.brand.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexarcano.dlrecord.brand.infrastructure.entities.BrandEntity;

@Repository
public interface JpaBrandRepository extends JpaRepository<BrandEntity, String> {
    long deleteByUuid(String uuid);
}
