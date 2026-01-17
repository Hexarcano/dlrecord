package com.hexarcano.dlrecord.maintainer.application.port.in;

import java.util.Optional;

import com.hexarcano.dlrecord.maintainer.infrastructure.controller.dto.UpdateMaintainerRequest;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

public interface IUpdateMaintainer {
    Optional<Maintainer> updateMaintainer(String uuid, UpdateMaintainerRequest request);
}
