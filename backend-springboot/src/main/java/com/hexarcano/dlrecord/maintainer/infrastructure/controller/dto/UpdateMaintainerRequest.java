package com.hexarcano.dlrecord.maintainer.infrastructure.controller.dto;

import com.hexarcano.dlrecord.maintainer.application.port.in.command.UpdateMaintainerCommand;

public record UpdateMaintainerRequest(String username, String email, String password, Boolean isAdmin) {
    public UpdateMaintainerCommand toUpdateMaintainerCommand() {
        return new UpdateMaintainerCommand(username, email, password, isAdmin);
    }
}
