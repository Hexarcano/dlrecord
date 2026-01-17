package com.hexarcano.dlrecord.maintainer.application.service;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.maintainer.application.port.in.ICreateMaintainer;
import com.hexarcano.dlrecord.maintainer.application.port.in.IDeleteMaintainer;
import com.hexarcano.dlrecord.maintainer.application.port.in.IRetrieveMaintainer;
import com.hexarcano.dlrecord.maintainer.application.port.in.IUpdateMaintainer;
import com.hexarcano.dlrecord.maintainer.infrastructure.controller.dto.UpdateMaintainerRequest;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MaintainerService implements ICreateMaintainer, IRetrieveMaintainer, IUpdateMaintainer, IDeleteMaintainer {
    private final ICreateMaintainer createMaintainer;
    private final IRetrieveMaintainer retrieveMaintainer;
    private final IUpdateMaintainer updateMaintainer;
    private final IDeleteMaintainer deleteMaintainer;

    @Override
    public Maintainer createMaintainer(Maintainer maintainer) {
        return createMaintainer.createMaintainer(maintainer);
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
    public Optional<Maintainer> updateMaintainer(String uuid, UpdateMaintainerRequest request) {
        return updateMaintainer.updateMaintainer(uuid, request);
    }

    @Override
    public boolean deleteMaintainer(String uuid) {
        return deleteMaintainer.deleteMaintainer(uuid);
    }
}