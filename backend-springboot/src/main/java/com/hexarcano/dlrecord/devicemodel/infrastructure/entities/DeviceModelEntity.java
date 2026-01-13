package com.hexarcano.dlrecord.devicemodel.infrastructure.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hexarcano.dlrecord.brand.infrastructure.entities.BrandEntity;
import com.hexarcano.dlrecord.devicemodel.model.entity.DeviceModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "device_model")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DeviceModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_uuid", referencedColumnName = "uuid", nullable = false)
    private BrandEntity brand;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public DeviceModelEntity(String uuid, String name, BrandEntity brand) {
        this.uuid = uuid;
        this.name = name;
        this.brand = brand;
    }

    public static DeviceModelEntity fromDomainModel(DeviceModel deviceModel) {
        BrandEntity brandEntity = BrandEntity.fromDomainModel(deviceModel.getBrand());

        return new DeviceModelEntity(
                deviceModel.getUuid(),
                deviceModel.getName(),
                brandEntity);
    }

    public DeviceModel toDomainModel() {
        return new DeviceModel(
                this.uuid,
                this.name,
                this.brand.toDomainModel());
    }
}
