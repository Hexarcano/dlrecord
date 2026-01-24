package com.hexarcano.dlrecord.maintainer.infrastructure.controller.dto;

import com.hexarcano.dlrecord.maintainer.application.port.in.command.CreateMaintainerCommand;

public record CreateMaintainerRequest(String username, String email, String password, Boolean isAdmin) {
    public CreateMaintainerCommand toCreateMaintainerCommand() {
        return new CreateMaintainerCommand(username, email, password, isAdmin);
    }
}
