package com.hexarcano.dlrecord.brand.infrastructure.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hexarcano.dlrecord.brand.model.entity.Brand;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Brand in the persistence layer.
 */
@Entity(name = "brand")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private String name;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public BrandEntity(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    /**
     * Maps a {@link Brand} domain model object to a {@link BrandEntity} persistence
     * object.
     * 
     * @param brand The domain model to convert.
     * @return A new {@link BrandEntity} instance with data from the domain model.
     */
    public static BrandEntity fromDomainModel(Brand brand) {
        return new BrandEntity(brand.getUuid(), brand.getName());
    }

    /**
     * Maps this {@link BrandEntity} persistence object to a {@link Brand} domain
     * model object.
     * 
     * @return A new {@link Brand} instance with data from this entity, representing
     *         the application's core domain.
     */
    public Brand toDomainModel() {
        return new Brand(uuid, name);
    }
}
