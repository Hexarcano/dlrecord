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

/**
 * Secondary (Driven) Adapter that implements the {@link IDeviceModelRepository}
 * output port.
 * 
 * <p>
 * It connects the application's core logic to the persistence layer using
 * Spring Data JPA. All methods operate on the {@link DeviceModel} domain model,
 * abstracting away the underlying persistence-specific {@link DeviceModelEntity}.
 * </p>
 */
@Repository
@AllArgsConstructor
public class JpaDeviceModelRepositoryAdapter implements IDeviceModelRepository {
    private final JpaDeviceModelRepository deviceModelrepository;
    private final JpaBrandRepository brandRepository;

    /**
     * Saves a {@link DeviceModel} domain model to the database.
     * 
     * @param deviceModel The {@link DeviceModel} domain object to save.
     * @return The persisted {@link DeviceModel} object, including any
     *         repository-generated values.
     */
    @Override
    @Transactional
    public DeviceModel save(DeviceModel deviceModel) {
        BrandEntity brandEntity = brandRepository.findById(deviceModel.getBrand().getUuid())
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));

        DeviceModelEntity deviceModelEntity = DeviceModelEntity.fromDomainModel(deviceModel);

        deviceModelEntity.setBrand(brandEntity);

        return deviceModelrepository.save(deviceModelEntity).toDomainModel();
    }

    /**
     * Finds a device model by its unique ID.
     *
     * @param uuid The unique identifier of the device model.
     * @return An {@link Optional} containing the {@link DeviceModel} if found,
     *         otherwise empty.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceModel> findById(String uuid) {
        return deviceModelrepository.findById(uuid).map(DeviceModelEntity::toDomainModel);
    }

    /**
     * Retrieves all device models from the database.
     *
     * @return A {@link List} of all {@link DeviceModel} domain models.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DeviceModel> findAll() {
        return deviceModelrepository.findAll().stream().map(DeviceModelEntity::toDomainModel).toList();
    }

    /**
     * Deletes a device model by its unique ID.
     *
     * @param uuid The unique identifier of the device model to delete.
     * @return {@code true} if a device model was deleted, {@code false} if no device
     *         model was found with the given ID.
     */
    @Override
    @Transactional
    public boolean deleteById(String uuid) {
        long affectedRows = deviceModelrepository.deleteByUuid(uuid);

        return affectedRows > 0;
    }

    /**
     * Counts the number of device models associated with a specific brand.
     *
     * @param brandUuid The unique identifier of the brand.
     * @return The count of device models for the given brand.
     */
    @Override
    @Transactional(readOnly = true)
    public long countByBrandUuid(String brandUuid) {
        return deviceModelrepository.countByBrand_Uuid(brandUuid);
    }
}
