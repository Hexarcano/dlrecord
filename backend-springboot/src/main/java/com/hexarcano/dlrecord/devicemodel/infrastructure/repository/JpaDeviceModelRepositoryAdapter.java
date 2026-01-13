package com.hexarcano.dlrecord.devicemodel.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hexarcano.dlrecord.brand.infrastructure.entities.BrandEntity;
import com.hexarcano.dlrecord.brand.infrastructure.repository.JpaBrandRepository;
import com.hexarcano.dlrecord.devicemodel.application.port.out.IDeviceModelRepository;
import com.hexarcano.dlrecord.devicemodel.infrastructure.entities.DeviceModelEntity;
import com.hexarcano.dlrecord.devicemodel.model.entity.DeviceModel;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class JpaDeviceModelRepositoryAdapter implements IDeviceModelRepository {
    private final JpaDeviceModelRepository deviceModelrepository;
    private final JpaBrandRepository brandRepository;

    @Override
    @Transactional
    public DeviceModel save(DeviceModel deviceModel) {
        BrandEntity brandEntity = brandRepository.findById(deviceModel.getBrand().getUuid())
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));

        DeviceModelEntity deviceTypeEntity = DeviceModelEntity.fromDomainModel(deviceModel);

        deviceTypeEntity.setBrand(brandEntity);

        return deviceModelrepository.save(deviceTypeEntity).toDomainModel();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceModel> findById(String uuid) {
        return deviceModelrepository.findById(uuid).map(DeviceModelEntity::toDomainModel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceModel> findAll() {
        return deviceModelrepository.findAll().stream().map(DeviceModelEntity::toDomainModel).toList();
    }

    @Override
    @Transactional
    public boolean deleteById(String uuid) {
        long affectedRows = deviceModelrepository.deleteByUuid(uuid);

        return affectedRows > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public long countByBrandUuid(String brandUuid) {
        return deviceModelrepository.countByBrand_Uuid(brandUuid);
    }
}
