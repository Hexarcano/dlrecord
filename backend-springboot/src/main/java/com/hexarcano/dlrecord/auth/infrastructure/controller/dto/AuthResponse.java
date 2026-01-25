package com.hexarcano.dlrecord.auth.infrastructure.controller.dto;

public record AuthResponse(String uuid, String username, String email, String token, Boolean isAdmin) {
}
