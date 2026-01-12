package com.hexarcano.dlrecord.devicetype.infrastructure.repository;

import com.hexarcano.dlrecord.devicetype.application.port.out.IDeviceTypeRepository;
import com.hexarcano.dlrecord.devicetype.infrastructure.entities.DeviceTypeEntity;
import com.hexarcano.dlrecord.devicetype.model.entity.DeviceType;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Secondary (Driven) Adapter that implements the {@link IDeviceTypeRepository} output port.
 * It connects the application's core logic to the persistence layer using Spring Data JPA.
 * All methods operate on the {@link DeviceType} domain model, abstracting away the underlying
 * persistence-specific {@link DeviceTypeEntity}.
 */
@AllArgsConstructor
public class JpaDeviceTypeRepositoryAdapter implements IDeviceTypeRepository {
    private final JpaDeviceTypeRepository repository;

    /**
     * Saves a {@link DeviceType} domain model to the database.
     * It maps the domain model to a {@link DeviceTypeEntity} before persisting.
     *
     * @param deviceType The {@link DeviceType} domain object to save.
     * @return The persisted {@link DeviceType} object, including any repository-generated values.
     */
    @Override
    @Transactional
    public DeviceType save(DeviceType deviceType) {
        DeviceTypeEntity entity = DeviceTypeEntity.fromDomainModel(deviceType);
        DeviceTypeEntity savedEntity = repository.save(entity);
        return savedEntity.toDomainModel();
    }

    /**
     * Finds a device type by its unique ID.
     * It retrieves a {@link DeviceTypeEntity} and maps it back to a {@link DeviceType} domain model.
     *
     * @param uuid The unique identifier of the device type.
     * @return An {@link Optional} containing the {@link DeviceType} if found, otherwise empty.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceType> findById(String uuid) {
        return repository.findById(uuid).map(DeviceTypeEntity::toDomainModel);
    }

    /**
     * Retrieves all device types from the database.
     * It maps the list of {@link DeviceTypeEntity} objects to a list of {@link DeviceType} domain models.
     *
     * @return A {@link List} of all {@link DeviceType} domain models.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DeviceType> findAll() {
        return repository.findAll().stream().map(DeviceTypeEntity::toDomainModel).collect(Collectors.toList());
    }

    /**
     * Finds a device type by its ID and updates its properties.
     * The operation is transactional. It retrieves the entity, updates its state from the domain model,
     * persists the changes, and maps the result back to a domain model.
     *
     * @param uuid The unique identifier of the device type to update.
     * @param deviceType The {@link DeviceType} domain object with the new data.
     * @return An {@link Optional} containing the updated {@link DeviceType} if found, otherwise empty.
     */
    @Override
    @Transactional
    public Optional<DeviceType> update(String uuid, DeviceType deviceType) {
        return repository.findById(uuid)
                .map(entity -> {
                    entity.setName(deviceType.getName());
                    DeviceTypeEntity saved = repository.save(entity);
                    return saved.toDomainModel();
                });
    }

    /**
     * Deletes a device type by its unique ID.
     *
     * @param uuid The unique identifier of the device type to delete.
     * @return {@code true} if a device type was deleted, {@code false} if no device type was found with the given ID.
     */
    @Override
    @Transactional
    public boolean deleteById(String uuid) {
        long affectedRows = repository.deleteByUuid(uuid);
        return affectedRows > 0;
    }
}
