package com.hexarcano.dlrecord.maintainer.application.port.in;

import java.util.Optional;

import com.hexarcano.dlrecord.maintainer.application.port.in.command.UpdateMaintainerCommand;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

public interface UpdateMaintainerUseCase {
    Optional<Maintainer> updateMaintainer(String uuid, UpdateMaintainerCommand command);
}
