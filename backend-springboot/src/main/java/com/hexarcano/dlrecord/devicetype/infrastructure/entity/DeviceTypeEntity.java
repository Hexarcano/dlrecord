package com.hexarcano.dlrecord.devicetype.infrastructure.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;

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
 * Represents a DeviceType in the persistence layer.
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
     * Maps a {@link DeviceType} domain model object to a {@link DeviceTypeEntity}
     * persistence object.
     *
     * @param deviceType The domain model to convert.
     * @return A new {@link DeviceTypeEntity} instance with data from the domain
     *         model.
     */
    public static DeviceTypeEntity fromDomainModel(DeviceType deviceType) {
        return new DeviceTypeEntity(deviceType.getUuid(), deviceType.getName());
    }

    /**
     * Maps this {@link DeviceTypeEntity} persistence object to a {@link DeviceType}
     * domain model object.
     *
     * @return A new {@link DeviceType} instance with data from this entity.
     */
    public DeviceType toDomainModel() {
        return new DeviceType(uuid, name);
    }
}
