package com.hexarcano.dlrecord.maintainer.application.implementation;

import com.hexarcano.dlrecord.maintainer.application.port.in.ICreateMaintainer;
import com.hexarcano.dlrecord.maintainer.application.port.out.IMaintainerRepository;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateMaintainer implements ICreateMaintainer {
    private final IMaintainerRepository repository;

    @Override
    public Maintainer createMaintainer(Maintainer maintainer) {
        return repository.save(maintainer);
    }

}
