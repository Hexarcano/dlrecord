package com.hexarcano.dlrecord.auth.infrastructure.controller.dto;

import com.hexarcano.dlrecord.auth.application.port.in.command.LoginCommand;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String username, @NotBlank String password) {
    public LoginCommand toCommand() {
        return new LoginCommand(username, password);
    }
}
