package com.hexarcano.dlrecord.maintainer.application.implementation;

import com.hexarcano.dlrecord.maintainer.application.port.in.DeleteMaintainerUseCase;
import com.hexarcano.dlrecord.maintainer.application.port.out.IMaintainerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteMaintainer implements DeleteMaintainerUseCase {
    private final IMaintainerRepository maintainerRepository;

    @Override
    public boolean deleteMaintainer(String uuid) {
        return maintainerRepository.delete(uuid);
    }

}
