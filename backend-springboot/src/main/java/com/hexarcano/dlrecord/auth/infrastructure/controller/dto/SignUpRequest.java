package com.hexarcano.dlrecord.auth.infrastructure.controller.dto;

import com.hexarcano.dlrecord.auth.application.port.in.command.SignUpCommand;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
        @NotBlank String username,
        @Email @NotBlank String email,
        @NotBlank String password,
        Boolean isAdmin) {

    public SignUpCommand toCommand() {
        return new SignUpCommand(username, email, password, isAdmin);
    }
}
