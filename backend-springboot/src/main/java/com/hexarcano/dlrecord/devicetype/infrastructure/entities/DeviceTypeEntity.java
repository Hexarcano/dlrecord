package com.hexarcano.dlrecord.devicetype.infrastructure.entities;

import com.hexarcano.dlrecord.devicetype.model.entity.DeviceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Represents a DeviceType in the persistence layer.
 * <p>
 * This is a JPA entity that will be mapped to a "device_type" table in the database.
 * It includes framework-specific annotations for persistence and auditing.
 * It is responsible for converting to and from the {@link DeviceType} domain model.
 * </p>
 */
@Entity(name = "device_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DeviceTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private String name;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public DeviceTypeEntity(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    /**
     * Maps a {@link DeviceType} domain model object to a {@link DeviceTypeEntity} persistence object.
     * This static factory method is a key part of the anti-corruption layer between the domain and infrastructure.
     *
     * @param deviceType The domain model to convert.
     * @return A new {@link DeviceTypeEntity} instance with data from the domain model.
     */
    public static DeviceTypeEntity fromDomainModel(DeviceType deviceType) {
        return new DeviceTypeEntity(deviceType.getUuid(), deviceType.getName());
    }

    /**
     * Maps this {@link DeviceTypeEntity} persistence object to a {@link DeviceType} domain model object.
     * This method ensures the application's core logic only works with pure domain objects.
     *
     * @return A new {@link DeviceType} instance with data from this entity.
     */
    public DeviceType toDomainModel() {
        return new DeviceType(uuid, name);
    }
}
