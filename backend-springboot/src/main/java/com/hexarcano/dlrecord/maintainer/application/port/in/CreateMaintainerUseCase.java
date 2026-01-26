package com.hexarcano.dlrecord.maintainer.application.port.in;

import com.hexarcano.dlrecord.maintainer.application.port.in.command.CreateMaintainerCommand;
import com.hexarcano.dlrecord.maintainer.domain.model.Maintainer;

public interface CreateMaintainerUseCase {
    Maintainer createMaintainer(CreateMaintainerCommand command);
}
