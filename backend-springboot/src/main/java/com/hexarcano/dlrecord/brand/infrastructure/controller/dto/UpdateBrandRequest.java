package com.hexarcano.dlrecord.brand.infrastructure.controller.dto;

import com.hexarcano.dlrecord.brand.application.port.in.command.UpdateBrandCommand;

public record UpdateBrandRequest(String name) {
    public UpdateBrandCommand toUpdateBrandCommand() {
        return new UpdateBrandCommand(name);
    }
}
