package com.hexarcano.dlrecord.maintainer.application.implementation;

import com.hexarcano.dlrecord.maintainer.application.port.in.IDeleteMaintainer;
import com.hexarcano.dlrecord.maintainer.application.port.out.IMaintainerRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteMaintainer implements IDeleteMaintainer {
    private final IMaintainerRepository repository;

    @Override
    public boolean deleteMaintainer(String uuid) {
        return repository.delete(uuid);
    }

}
