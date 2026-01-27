package com.hexarcano.dlrecord.device.infrastructure.adapter;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.hexarcano.dlrecord.brand.domain.model.Brand;
import com.hexarcano.dlrecord.device.application.port.out.DeviceRepositoryPort;
import com.hexarcano.dlrecord.device.domain.model.Device;
import com.hexarcano.dlrecord.device.infrastructure.entity.DeviceEntity;
import com.hexarcano.dlrecord.device.infrastructure.repository.JpaDeviceRepository;
import com.hexarcano.dlrecord.brand.infrastructure.repository.JpaBrandRepository;
import com.hexarcano.dlrecord.devicemodel.infrastructure.repository.JpaDeviceModelRepository;
import com.hexarcano.dlrecord.devicetype.infrastructure.repository.JpaDeviceTypeRepository;
import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;
import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;
import com.hexarcano.dlrecord.brand.infrastructure.entity.BrandEntity;
import com.hexarcano.dlrecord.devicemodel.infrastructure.entity.DeviceModelEntity;
import com.hexarcano.dlrecord.devicetype.infrastructure.entity.DeviceTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaDeviceRepositoryAdapter implements DeviceRepositoryPort {
    private final JpaDeviceRepository deviceRepository;
    private final JpaBrandRepository brandRepository;
    private final JpaDeviceModelRepository deviceModelRepository;
    private final JpaDeviceTypeRepository deviceTypeRepository;

    @Override
    @Transactional
    public Device save(Device device) {
        DeviceEntity entity = DeviceEntity.fromDomainModel(device);

        entity.setBrand(brandRepository.getReferenceById(device.getBrand().getUuid()));
        entity.setDeviceModel(deviceModelRepository.getReferenceById(device.getDeviceModel().getUuid()));
        entity.setDeviceType(deviceTypeRepository.getReferenceById(device.getDeviceType().getUuid()));

        return deviceRepository.save(entity).toDomainModel();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Device> findById(String uuid) {
        return deviceRepository.findById(uuid).map(DeviceEntity::toDomainModel);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Device> findAll(Pageable pageable) {
        return deviceRepository.findAll(pageable).map(DeviceEntity::toDomainModel);
    }

    @Override
    @Transactional
    public boolean deleteById(String uuid) {
        long affectedRows = deviceRepository.deleteByUuid(uuid);
        return affectedRows > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Brand> findBrandById(String id) {
        return brandRepository.findById(id)
                .map(BrandEntity::toDomainModel);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceModel> findDeviceModelById(String id) {
        return deviceModelRepository.findById(id)
                .map(DeviceModelEntity::toDomainModel);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceType> findDeviceTypeById(String id) {
        return deviceTypeRepository.findById(id)
                .map(DeviceTypeEntity::toDomainModel);
    }
}
