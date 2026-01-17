package com.hexarcano.dlrecord.maintainer.application.implementation;

import java.util.Optional;

import com.hexarcano.dlrecord.maintainer.application.port.in.IUpdateMaintainer;
import com.hexarcano.dlrecord.maintainer.application.port.out.IMaintainerRepository;
import com.hexarcano.dlrecord.maintainer.infrastructure.controller.dto.UpdateMaintainerRequest;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateMaintainer implements IUpdateMaintainer {
    private final IMaintainerRepository repository;

    @Override
    public Optional<Maintainer> updateMaintainer(String uuid, UpdateMaintainerRequest request) {
        return repository.findById(uuid).map(maintainerToUpdate -> {
            if (request.username() != null && !request.username().equals(maintainerToUpdate.getUsername())) {
                maintainerToUpdate.changeUsername(request.username());
            }

            if (request.email() != null && !request.email().equals(maintainerToUpdate.getEmail())) {
                maintainerToUpdate.changeEmail(request.email());
            }

            if (request.password() != null && !request.password().equals(maintainerToUpdate.getPassword())) {
                maintainerToUpdate.changePassword(request.password());
            }

            if (request.isAdmin() != null && request.isAdmin() != maintainerToUpdate.getIsAdmin()) {
                maintainerToUpdate.changeIsAdmin(request.isAdmin());
            }

            return repository.save(maintainerToUpdate);
        });
    }

}
