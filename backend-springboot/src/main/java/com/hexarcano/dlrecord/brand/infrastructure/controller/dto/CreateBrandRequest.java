package com.hexarcano.dlrecord.brand.infrastructure.controller.dto;

import com.hexarcano.dlrecord.brand.application.port.in.command.CreateBrandCommand;

public record CreateBrandRequest(String name) {
    public CreateBrandCommand toCreateBrandCommand() {
        return new CreateBrandCommand(name);
    }
}
