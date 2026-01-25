package com.hexarcano.dlrecord.auth.infrastructure.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(@NotBlank String refreshToken) {
}
