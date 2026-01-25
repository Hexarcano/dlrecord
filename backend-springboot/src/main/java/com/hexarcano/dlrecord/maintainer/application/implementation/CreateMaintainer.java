package com.hexarcano.dlrecord.maintainer.application.implementation;

import com.hexarcano.dlrecord.maintainer.application.port.in.CreateMaintainerUseCase;
import com.hexarcano.dlrecord.maintainer.application.port.in.command.CreateMaintainerCommand;
import com.hexarcano.dlrecord.maintainer.application.port.out.MaintainerRepositoryPort;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateMaintainer implements CreateMaintainerUseCase {
    private final MaintainerRepositoryPort maintainerRepository;

    @Override
    public Maintainer createMaintainer(CreateMaintainerCommand command) {
        Maintainer maintainer = new Maintainer(null, command.username(), command.email(), command.password(),
                command.isAdmin());

        return maintainerRepository.save(maintainer);
    }

}
