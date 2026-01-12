package com.hexarcano.dlrecord.brand.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.hexarcano.dlrecord.brand.application.port.out.IBrandRepository;
import com.hexarcano.dlrecord.brand.infrastructure.entities.BrandEntity;
import com.hexarcano.dlrecord.brand.model.entity.Brand;

import lombok.AllArgsConstructor;

/**
 * Secondary (Driven) Adapter that implements the {@link IBrandRepository}
 * output port.
 * 
 * <p>
 * It connects the application's core logic to the persistence layer using
 * Spring Data JPA. All methods operate on the {@link Brand} domain model,
 * abstracting away the underlying persistence-specific {@link BrandEntity}.
 * </p>
 */
@AllArgsConstructor
public class JpaBrandRepositoryAdapter implements IBrandRepository {
    private final JpaBrandRepository repository;

    /**
     * Saves a {@link Brand} domain model to the database.
     * 
     * @param brand The {@link Brand} domain object to save.
     * @return The persisted {@link Brand} object, including any
     *         repository-generated values.
     */
    @Override
    @Transactional
    public Brand save(Brand brand) {
        BrandEntity entity = BrandEntity.fromDomainModel(brand);

        return repository.save(entity).toDomainModel();
    }

    /**
     * Finds a brand by its unique ID.
     *
     * @param uuid The unique identifier of the brand.
     * @return An {@link Optional} containing the {@link Brand} if found, otherwise
     *         empty.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Brand> findById(String uuid) {
        return repository.findById(uuid).map(BrandEntity::toDomainModel);
    }

    /**
     * Retrieves all brands from the database.
     *
     * @return A {@link List} of all {@link Brand} domain models.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Brand> findAll() {
        return repository.findAll().stream().map(BrandEntity::toDomainModel).toList();
    }

    /**
     * Finds a brand by its ID and updates its properties.
     *
     * @param uuid  The unique identifier of the brand to update.
     * @param brand The {@link Brand} domain object with the new data.
     * @return An {@link Optional} containing the updated {@link Brand} if found,
     *         otherwise empty.
     */
    @Override
    @Transactional
    public Optional<Brand> update(String uuid, Brand brand) {
        return repository.findById(uuid)
                .map(entity -> {
                    entity.setName(brand.getName());

                    return repository.save(entity).toDomainModel();
                });
    }

    /**
     * Deletes a brand by its unique ID.
     *
     * @param uuid The unique identifier of the brand to delete.
     * @return {@code true} if a brand was deleted, {@code false} if no brand was
     *         found with the given ID.
     */
    @Override
    @Transactional
    public boolean deleteById(String uuid) {
        long affectedRows = repository.deleteByUuid(uuid);

        return affectedRows > 0;
    }
}
