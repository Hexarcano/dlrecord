package com.hexarcano.dlrecord.maintainer.application.implementation;

import java.util.Optional;

import com.hexarcano.dlrecord.maintainer.application.port.in.UpdateMaintainerUseCase;
import com.hexarcano.dlrecord.maintainer.application.port.in.command.UpdateMaintainerCommand;
import com.hexarcano.dlrecord.maintainer.application.port.out.MaintainerRepositoryPort;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateMaintainer implements UpdateMaintainerUseCase {
    private final MaintainerRepositoryPort maintainerRepository;

    @Override
    public Optional<Maintainer> updateMaintainer(String uuid, UpdateMaintainerCommand command) {
        return maintainerRepository.findById(uuid).map(maintainerToUpdate -> {
            boolean updated = false;

            if (command.username() != null && !command.username().equals(maintainerToUpdate.getUsername())) {
                maintainerToUpdate.changeUsername(command.username());

                updated = true;
            }

            if (command.email() != null && !command.email().equals(maintainerToUpdate.getEmail())) {
                maintainerToUpdate.changeEmail(command.email());

                updated = true;
            }

            if (command.password() != null && !command.password().equals(maintainerToUpdate.getPassword())) {
                maintainerToUpdate.changePassword(command.password());

                updated = true;
            }

            if (command.isAdmin() != null && command.isAdmin() != maintainerToUpdate.getIsAdmin()) {
                maintainerToUpdate.changeIsAdmin(command.isAdmin());

                updated = true;
            }

            if (updated) {
                return maintainerRepository.save(maintainerToUpdate);
            }

            return maintainerToUpdate;
        });
    }

}
