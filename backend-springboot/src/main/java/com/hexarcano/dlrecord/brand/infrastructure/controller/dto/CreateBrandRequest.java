package com.hexarcano.dlrecord.brand.infrastructure.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.hexarcano.dlrecord.brand.application.port.in.command.CreateBrandCommand;

public record CreateBrandRequest(
        @NotBlank(message = "Brand name is required") @Size(max = 100, message = "Brand name must not exceed 100 characters") String name) {
    public CreateBrandCommand toCreateBrandCommand() {
        return new CreateBrandCommand(name);
    }
}
