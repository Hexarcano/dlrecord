package com.hexarcano.dlrecord.brand.infrastructure.adapter;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.hexarcano.dlrecord.brand.application.port.out.BrandRepositoryPort;
import com.hexarcano.dlrecord.brand.domain.model.Brand;
import com.hexarcano.dlrecord.brand.infrastructure.entity.BrandEntity;
import com.hexarcano.dlrecord.brand.infrastructure.repository.JpaBrandRepository;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

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
@Repository
@RequiredArgsConstructor
public class JpaBrandRepositoryAdapter implements BrandRepositoryPort {
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
     * Retrieves all brands from the database with pagination.
     *
     * @param pageable The pagination information.
     * @return A {@link Page} of all {@link Brand} domain models.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Brand> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(BrandEntity::toDomainModel);
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

    @Override
    public boolean existsById(String uuid) {
        return repository.existsById(uuid);
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }
}
