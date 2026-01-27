package com.hexarcano.dlrecord.device.domain.model;

import java.util.regex.Pattern;

import com.hexarcano.dlrecord.brand.domain.model.Brand;
import com.hexarcano.dlrecord.device.domain.exception.DeviceInvalidDataException;
import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;
import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;

import lombok.Getter;

@Getter
public class Device {
    private static final String IPV4_PATTERN = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])(\\/([0-9]|[1-2][0-9]|3[0-2]))$";
    private static final String MAC_PATTERN = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";

    private final String uuid;
    private String deviceName;
    private String ipv4;
    private String ipv6;
    private String macAddress;
    private Brand brand;
    private DeviceModel deviceModel;
    private DeviceType deviceType;
    private String inventoryName;

    public Device(String uuid, String deviceName, String ipv4, String ipv6, String macAddress,
            Brand brand, DeviceModel deviceModel, DeviceType deviceType, String inventoryName) {
        validateDeviceName(deviceName);
        validateIpv4(ipv4);
        validateIpv6(ipv6);
        validateMacAddress(macAddress);
        validateBrand(brand);
        validateDeviceModel(deviceModel);
        validateDeviceType(deviceType);
        validateInventoryName(inventoryName);

        this.uuid = uuid;
        this.deviceName = deviceName;
        this.ipv4 = ipv4;
        this.ipv6 = ipv6;
        this.macAddress = macAddress;
        this.brand = brand;
        this.deviceModel = deviceModel;
        this.deviceType = deviceType;
        this.inventoryName = inventoryName;
    }

    private void validateDeviceName(String deviceName) {
        if (deviceName == null || deviceName.isBlank()) {
            throw new DeviceInvalidDataException("Device Name is required");
        }
    }

    private void validateIpv4(String ipv4) {
        if (ipv4 == null || ipv4.isBlank()) {
            throw new DeviceInvalidDataException("IPv4 is required");
        }
        if (!isValidIpv4(ipv4)) {
            throw new DeviceInvalidDataException("Invalid IPv4 format");
        }
    }

    private void validateIpv6(String ipv6) {
        if (ipv6 != null && !ipv6.isBlank() && !isValidIpv6(ipv6)) {
            throw new DeviceInvalidDataException("Invalid IPv6 format");
        }
    }

    private void validateMacAddress(String macAddress) {
        if (macAddress != null && !macAddress.isBlank() && !isValidMacAddress(macAddress)) {
            throw new DeviceInvalidDataException("Invalid MAC Address format");
        }
    }

    private void validateBrand(Brand brand) {
        if (brand == null) {
            throw new DeviceInvalidDataException("Brand is required");
        }
    }

    private void validateDeviceModel(DeviceModel deviceModel) {
        if (deviceModel == null) {
            throw new DeviceInvalidDataException("Device Model is required");
        }
    }

    private void validateDeviceType(DeviceType deviceType) {
        if (deviceType == null) {
            throw new DeviceInvalidDataException("Device Type is required");
        }
    }

    private void validateInventoryName(String inventoryName) {
        if (inventoryName == null || inventoryName.isBlank()) {
            throw new DeviceInvalidDataException("Inventory Name is required");
        }
    }

    private boolean isValidIpv4(String ip) {
        return Pattern.compile(IPV4_PATTERN).matcher(ip).matches();
    }

    private boolean isValidIpv6(String ip) {
        return ip.contains(":") && ip.split(":").length <= 8;
    }

    private boolean isValidMacAddress(String mac) {
        return Pattern.compile(MAC_PATTERN).matcher(mac).matches();
    }

    public void changeDeviceName(String newDeviceName) {
        validateDeviceName(newDeviceName);

        this.deviceName = newDeviceName;
    }

    public void changeIpv4(String newIpv4) {
        validateIpv4(newIpv4);

        this.ipv4 = newIpv4;
    }

    public void changeIpv6(String newIpv6) {
        validateIpv6(newIpv6);

        this.ipv6 = newIpv6;
    }

    public void changeMacAddress(String newMacAddress) {
        validateMacAddress(newMacAddress);

        this.macAddress = newMacAddress;
    }

    public void changeBrand(Brand newBrand) {
        validateBrand(newBrand);

        this.brand = newBrand;
    }

    public void changeDeviceModel(DeviceModel newDeviceModel) {
        validateDeviceModel(newDeviceModel);

        this.deviceModel = newDeviceModel;
    }

    public void changeDeviceType(DeviceType newDeviceType) {
        validateDeviceType(newDeviceType);

        this.deviceType = newDeviceType;
    }

    public void changeInventoryName(String newInventoryName) {
        validateInventoryName(newInventoryName);

        this.inventoryName = newInventoryName;
    }
}
