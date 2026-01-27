package com.hexarcano.dlrecord.device.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexarcano.dlrecord.device.infrastructure.entity.DeviceEntity;

@Repository
public interface JpaDeviceRepository extends JpaRepository<DeviceEntity, String> {
    long deleteByUuid(String uuid);
}
