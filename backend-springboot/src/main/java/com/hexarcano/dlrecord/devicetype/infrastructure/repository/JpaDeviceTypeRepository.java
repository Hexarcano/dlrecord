package com.hexarcano.dlrecord.devicetype.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexarcano.dlrecord.devicetype.infrastructure.entities.DeviceTypeEntity;

@Repository
public interface JpaDeviceTypeRepository extends JpaRepository<DeviceTypeEntity, String> {
    long deleteByUuid(String uuid);
}
