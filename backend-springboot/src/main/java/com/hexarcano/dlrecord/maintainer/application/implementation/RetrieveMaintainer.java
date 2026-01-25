package com.hexarcano.dlrecord.maintainer.application.implementation;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.maintainer.application.port.in.RetrieveMaintainerUseCase;
import com.hexarcano.dlrecord.maintainer.application.port.out.MaintainerRepositoryPort;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RetrieveMaintainer implements RetrieveMaintainerUseCase {
    private final MaintainerRepositoryPort maintainerRepository;

    @Override
    public Optional<Maintainer> findById(String uuid) {
        return maintainerRepository.findById(uuid);
    }

    @Override
    public List<Maintainer> findAll() {
        return maintainerRepository.findAll();
    }
}
