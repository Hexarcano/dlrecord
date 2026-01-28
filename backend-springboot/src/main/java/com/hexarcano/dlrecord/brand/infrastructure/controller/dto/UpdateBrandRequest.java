package com.hexarcano.dlrecord.brand.infrastructure.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.hexarcano.dlrecord.brand.application.port.in.command.UpdateBrandCommand;

/**
 * Request DTO for updating a brand via PUT.
 * 
 * <p>
 * For PUT requests, all fields are required as per REST standard.
 * Use PATCH for partial updates.
 * </p>
 */
public record UpdateBrandRequest(
        @NotBlank(message = "Brand name is required") @Size(max = 100, message = "Brand name must not exceed 100 characters") String name) {
    public UpdateBrandCommand toUpdateBrandCommand() {
        return new UpdateBrandCommand(name);
    }
}
