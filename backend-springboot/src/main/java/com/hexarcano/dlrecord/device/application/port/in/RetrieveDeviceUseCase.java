package com.hexarcano.dlrecord.device.application.port.in;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hexarcano.dlrecord.device.domain.model.Device;

public interface RetrieveDeviceUseCase {
    Optional<Device> findById(String uuid);

    Page<Device> findAll(Pageable pageable);
}
