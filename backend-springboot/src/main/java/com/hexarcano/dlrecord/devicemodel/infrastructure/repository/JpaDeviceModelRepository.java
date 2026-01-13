package com.hexarcano.dlrecord.devicemodel.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexarcano.dlrecord.devicemodel.infrastructure.entities.DeviceModelEntity;

@Repository
public interface JpaDeviceModelRepository extends JpaRepository<DeviceModelEntity, String> {
    long deleteByUuid(String uuid);

    long countByBrand_Uuid(String brandUuid);
}
