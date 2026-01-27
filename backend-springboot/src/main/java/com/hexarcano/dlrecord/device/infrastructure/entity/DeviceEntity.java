package com.hexarcano.dlrecord.device.infrastructure.entity;

import com.hexarcano.dlrecord.brand.infrastructure.entity.BrandEntity;
import com.hexarcano.dlrecord.device.domain.model.Device;
import com.hexarcano.dlrecord.devicemodel.infrastructure.entity.DeviceModelEntity;
import com.hexarcano.dlrecord.devicetype.infrastructure.entity.DeviceTypeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "device")
public class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", nullable = false, length = 36)
    private String uuid;

    @Column(name = "device_name", nullable = false)
    private String deviceName;

    @Column(name = "ipv4", nullable = false)
    private String ipv4;

    @Column(name = "ipv6")
    private String ipv6;

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "inventory_name", nullable = false)
    private String inventoryName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandEntity brand;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "device_model_id", nullable = false)
    private DeviceModelEntity deviceModel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "device_type_id", nullable = false)
    private DeviceTypeEntity deviceType;

    public static DeviceEntity fromDomainModel(Device device) {
        return DeviceEntity.builder()
                .uuid(device.getUuid())
                .deviceName(device.getDeviceName())
                .ipv4(device.getIpv4())
                .ipv6(device.getIpv6())
                .macAddress(device.getMacAddress())
                .inventoryName(device.getInventoryName())
                .brand(BrandEntity.fromDomainModel(device.getBrand()))
                .deviceModel(DeviceModelEntity.fromDomainModel(device.getDeviceModel()))
                .deviceType(DeviceTypeEntity.fromDomainModel(device.getDeviceType()))
                .build();
    }

    public Device toDomainModel() {
        return new Device(
                this.uuid,
                this.deviceName,
                this.ipv4,
                this.ipv6,
                this.macAddress,
                this.brand.toDomainModel(),
                this.deviceModel.toDomainModel(),
                this.deviceType.toDomainModel(),
                this.inventoryName);
    }
}
