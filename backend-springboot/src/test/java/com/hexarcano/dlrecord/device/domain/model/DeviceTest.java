package com.hexarcano.dlrecord.device.domain.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import com.hexarcano.dlrecord.brand.domain.model.Brand;
import com.hexarcano.dlrecord.device.domain.exception.DeviceInvalidDataException;
import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;
import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;

class DeviceTest {

    @Test
    void shouldCreateDevice_WhenAllFieldsAreValid() {
        Brand brand = mock(Brand.class);
        DeviceModel model = mock(DeviceModel.class);
        DeviceType type = mock(DeviceType.class);

        assertDoesNotThrow(() -> new Device(
                "uuid-1",
                "My Device",
                "192.168.1.1/24",
                "fe80::1",
                "00:1A:2B:3C:4D:5E",
                brand,
                model,
                type,
                "inventory-1"));
    }

    @Test
    void shouldCreateDevice_WhenIpv4HasCidrSuffix() {
        Brand brand = mock(Brand.class);
        DeviceModel model = mock(DeviceModel.class);
        DeviceType type = mock(DeviceType.class);

        Device device = new Device(
                "uuid-1",
                "My Device",
                "192.168.1.1/24",
                null,
                null,
                brand,
                model,
                type,
                "inventory-1");

        assertEquals("192.168.1.1/24", device.getIpv4());
    }

    @Test
    void shouldThrowException_WhenIpv4IsPlain() {
        Brand brand = mock(Brand.class);
        DeviceModel model = mock(DeviceModel.class);
        DeviceType type = mock(DeviceType.class);

        assertThrows(DeviceInvalidDataException.class, () -> new Device(
                "uuid-1",
                "My Device",
                "192.168.1.1",
                null,
                null,
                brand,
                model,
                type,
                "inventory-1"));
    }

    @Test
    void shouldThrowException_WhenDeviceNameIsInvalid() {
        Brand brand = mock(Brand.class);
        DeviceModel model = mock(DeviceModel.class);
        DeviceType type = mock(DeviceType.class);

        assertThrows(DeviceInvalidDataException.class, () -> new Device(
                null,
                "", // Invalid: Blank
                "192.168.1.1/24",
                null,
                null,
                brand,
                model,
                type,
                "inventory-1"));
    }

    @Test
    void shouldThrowException_WhenIpv4IsInvalid() {
        Brand brand = mock(Brand.class);
        DeviceModel model = mock(DeviceModel.class);
        DeviceType type = mock(DeviceType.class);

        assertThrows(DeviceInvalidDataException.class, () -> new Device(
                null,
                "Device",
                "999.999.999.999", // Invalid IP
                null,
                null,
                brand,
                model,
                type,
                "inventory-1"));
    }

    @Test
    void shouldThrowException_WhenIpv4CidrIsInvalid() {
        Brand brand = mock(Brand.class);
        DeviceModel model = mock(DeviceModel.class);
        DeviceType type = mock(DeviceType.class);

        assertThrows(DeviceInvalidDataException.class, () -> new Device(
                null,
                "Device",
                "192.168.1.1/33", // Invalid CIDR > 32
                null,
                null,
                brand,
                model,
                type,
                "inventory-1"));
    }

    @Test
    void shouldThrowException_WhenIpv6IsInvalid() {
        Brand brand = mock(Brand.class);
        DeviceModel model = mock(DeviceModel.class);
        DeviceType type = mock(DeviceType.class);

        assertThrows(DeviceInvalidDataException.class, () -> new Device(
                null,
                "Device",
                "192.168.1.1/24",
                "invalid-ipv6", // Invalid IPv6
                null,
                brand,
                model,
                type,
                "inventory-1"));
    }

    @Test
    void shouldThrowException_WhenMacAddressIsInvalid() {
        Brand brand = mock(Brand.class);
        DeviceModel model = mock(DeviceModel.class);
        DeviceType type = mock(DeviceType.class);

        assertThrows(DeviceInvalidDataException.class, () -> new Device(
                null,
                "Device",
                "192.168.1.1/24",
                null,
                "ZZ:ZZ:ZZ:ZZ:ZZ:ZZ", // Invalid MAC
                brand,
                model,
                type,
                "inventory-1"));
    }

    @Test
    void shouldChangeIpv4_WhenFormatIsValid() {
        Brand brand = mock(Brand.class);
        DeviceModel model = mock(DeviceModel.class);
        DeviceType type = mock(DeviceType.class);

        Device device = new Device(
                "uuid-1", "Device", "1.1.1.1/32", null, null, brand, model, type, "inv");

        device.changeIpv4("192.168.0.1/32");

        assertEquals("192.168.0.1/32", device.getIpv4());
    }

    @Test
    void shouldFailToChangeIpv4_WhenFormatIsInvalid() {
        Brand brand = mock(Brand.class);
        DeviceModel model = mock(DeviceModel.class);
        DeviceType type = mock(DeviceType.class);

        Device device = new Device(
                "uuid-1", "Device", "1.1.1.1/32", null, null, brand, model, type, "inv");

        assertThrows(DeviceInvalidDataException.class, () -> device.changeIpv4("bad-ip"));
        assertThrows(DeviceInvalidDataException.class, () -> device.changeIpv4("bad-ip"));
    }

    @Test
    void shouldCreateDevice_WhenMacAddressIsValid() {
        Brand brand = mock(Brand.class);
        DeviceModel model = mock(DeviceModel.class);
        DeviceType type = mock(DeviceType.class);

        Device device = new Device(
                "uuid-1", "Device", "192.168.1.1/24", null, "AA:BB:CC:DD:EE:FF", brand, model, type, "inv");

        assertEquals("AA:BB:CC:DD:EE:FF", device.getMacAddress());
    }

    @Test
    void shouldChangeMacAddress_WhenFormatIsValid() {
        Brand brand = mock(Brand.class);
        DeviceModel model = mock(DeviceModel.class);
        DeviceType type = mock(DeviceType.class);

        Device device = new Device(
                "uuid-1", "Device", "192.168.1.1/24", null, "00:00:00:00:00:00", brand, model, type, "inv");

        device.changeMacAddress("11:22:33:44:55:66");

        assertEquals("11:22:33:44:55:66", device.getMacAddress());
    }

    @Test
    void shouldFailToChangeMacAddress_WhenFormatIsInvalid() {
        Brand brand = mock(Brand.class);
        DeviceModel model = mock(DeviceModel.class);
        DeviceType type = mock(DeviceType.class);

        Device device = new Device(
                "uuid-1", "Device", "192.168.1.1/24", null, "00:00:00:00:00:00", brand, model, type, "inv");

        assertThrows(DeviceInvalidDataException.class, () -> device.changeMacAddress("invalid-mac"));
    }
}
