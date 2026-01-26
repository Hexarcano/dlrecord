package com.hexarcano.dlrecord.maintainer.application.service;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.maintainer.application.port.in.CreateMaintainerUseCase;
import com.hexarcano.dlrecord.maintainer.application.port.in.DeleteMaintainerUseCase;
import com.hexarcano.dlrecord.maintainer.application.port.in.RetrieveMaintainerUseCase;
import com.hexarcano.dlrecord.maintainer.application.port.in.UpdateMaintainerUseCase;
import com.hexarcano.dlrecord.maintainer.application.port.in.command.CreateMaintainerCommand;
import com.hexarcano.dlrecord.maintainer.application.port.in.command.UpdateMaintainerCommand;
import com.hexarcano.dlrecord.maintainer.domain.model.Maintainer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MaintainerService implements CreateMaintainerUseCase, RetrieveMaintainerUseCase, UpdateMaintainerUseCase,
        DeleteMaintainerUseCase {
    private final CreateMaintainerUseCase createMaintainer;
    private final RetrieveMaintainerUseCase retrieveMaintainer;
    private final UpdateMaintainerUseCase updateMaintainer;
    private final DeleteMaintainerUseCase deleteMaintainer;

    @Override
    public Maintainer createMaintainer(CreateMaintainerCommand command) {
        return createMaintainer.createMaintainer(command);
    }

    @Override
    public Optional<Maintainer> findById(String uuid) {
        return retrieveMaintainer.findById(uuid);
    }

    @Override
    public List<Maintainer> findAll() {
        return retrieveMaintainer.findAll();
    }

    @Override
    public Optional<Maintainer> updateMaintainer(String uuid, UpdateMaintainerCommand command) {
        return updateMaintainer.updateMaintainer(uuid, command);
    }

    @Override
    public boolean deleteMaintainer(String uuid) {
        return deleteMaintainer.deleteMaintainer(uuid);
    }
}