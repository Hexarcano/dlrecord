package com.hexarcano.dlrecord.maintainer.application.implementation;

import com.hexarcano.dlrecord.maintainer.application.port.in.DeleteMaintainerUseCase;
import com.hexarcano.dlrecord.maintainer.application.port.out.MaintainerRepositoryPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteMaintainer implements DeleteMaintainerUseCase {
    private final MaintainerRepositoryPort maintainerRepository;

    @Override
    public boolean deleteMaintainer(String uuid) {
        return maintainerRepository.delete(uuid);
    }

}
