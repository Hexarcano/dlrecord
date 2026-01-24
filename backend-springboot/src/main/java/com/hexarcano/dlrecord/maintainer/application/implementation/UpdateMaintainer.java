package com.hexarcano.dlrecord.maintainer.application.implementation;

import java.util.Optional;

import com.hexarcano.dlrecord.maintainer.application.port.in.UpdateMaintainerUseCase;
import com.hexarcano.dlrecord.maintainer.application.port.in.command.UpdateMaintainerCommand;
import com.hexarcano.dlrecord.maintainer.application.port.out.IMaintainerRepository;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateMaintainer implements UpdateMaintainerUseCase {
    private final IMaintainerRepository maintainerRepository;

    @Override
    public Optional<Maintainer> updateMaintainer(String uuid, UpdateMaintainerCommand command) {
        return maintainerRepository.findById(uuid).map(maintainerToUpdate -> {
            if (command.username() != null && !command.username().equals(maintainerToUpdate.getUsername())) {
                maintainerToUpdate.changeUsername(command.username());
            }

            if (command.email() != null && !command.email().equals(maintainerToUpdate.getEmail())) {
                maintainerToUpdate.changeEmail(command.email());
            }

            if (command.password() != null && !command.password().equals(maintainerToUpdate.getPassword())) {
                maintainerToUpdate.changePassword(command.password());
            }

            if (command.isAdmin() != null && command.isAdmin() != maintainerToUpdate.getIsAdmin()) {
                maintainerToUpdate.changeIsAdmin(command.isAdmin());
            }

            return maintainerRepository.save(maintainerToUpdate);
        });
    }

}
